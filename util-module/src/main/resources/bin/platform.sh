#!/bin/sh

# Author:  platform
# Date:    2020-11-29
# Version: V1.0.0

# ---------------------------------------------------------------------------------------------
# 变量说明
#
#   STATUS_CHECK_HTTP    状态检测地址（HTTP）
#
#   STATUS_CHECK_TCP     状态检测地址（TCP）
#
#   BIN_DIR              脚本所在目录，如执行脚本为 /opt/uyun/platform/fs/bin/fs.sh，
#                        则 BIN_DIR=/opt/uyun/platform/fs/bin/
#
#   WORK_HOME            安装目录，BIN_DIR的上级目录，可根据实际情况修改
#
#   JAVA_HOME            JDK安装目录，一般无需配该参数，如有需要可通过命令行参数设置
#
#   RUNNING_USER         运行时用户，默认为 uyun，可根据实际情况修改，可通过命令行参数设置
#
#   MAIN_CLASS           JAVA Main函数所在类名，可选，如果指定了该值，将通过此Main Class启动Java应用
#
#   APP_NAME             应用名称，取脚本名称（脚本名称需与模块名称保持一致）
#
#   CLASSPATH            可根据实际情况修改
#
#   JAVA_OPTS            Java命令行参数，可根据实际情况修改，可通过命令行参数设置
#
#   DEBUG_PORT           远程DEBUG端口，默认8008，可通过命令行参数设置
#
# ---------------------------------------------------------------------------------------------


source ~/.bash_profile
umask 077

get_local_addr() {
    addr=127.0.0.1
    if [ -n "$dubbo_registry_localAddress" ] ; then
        addr=$dubbo_registry_localAddress
    fi
    echo $addr
}

local_ip=$(get_local_addr)

STATUS_CHECK_HTTP="http://$local_ip:8080"

BIN_DIR=$(cd "$(dirname "$0")"; pwd)
WORK_HOME=$(dirname $BIN_DIR)
# 当前服务的脚本名称
SCRIPT_NAME=`echo $0 | awk -F '/' '{print $NF}'`
# 模块名称
APP_NAME=data-module
# 主类
MAIN_CLASS=com.zcs.module.demo.ModuleApplication
DEBUG_PORT=8000
# CLASSPATH模块分为普通模块和对应的定制lib
CLASSPATH=":$WORK_HOME/custom/*:$WORK_HOME/lib/*"

# ---------------------------------------------------------------------------------------------
# JAVA_OPTS Java命令行参数说明
# ----------------------------------------------------------------------------------------------
JAVA_OPTS="-Dapp.name=$APP_NAME -Dwork.dir=$WORK_HOME -Djasypt.encryptor.password=ah0b6RZwIvJFUwZ8cVubCA -Dspring.profiles.active=prod \
           -Dlogging.config=$WORK_HOME/config/spring-logback.xml -Dspring.config.location=file:$WORK_HOME/config/ \
           -Djava.security.egd=file:/dev/./urandom -Xmx512m -Xms256m -Xss512K -XX:MaxMetaspaceSize=256m \
           -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:+ExplicitGCInvokesConcurrent"

usage() {
    echo "Usage: $SCRIPT_NAME ( commands ... )"
    echo "commands:"
    echo "  -c   command name, optional value: run|start|stop|restart|debug|status,"
    echo "       can also call it by '$SCRIPT_NAME start', but command must be the first positional parameter."
    echo "  -m   java home"
    echo "  -d   java opts"
    echo "  -u   running user"
    echo "  -p   debug port"
    echo "  -h   help"
}

# 解析命令行参数
# positional parameters
ARGS=()
while [ $# -gt 0 ]
do
    unset OPTIND
    unset OPTARG
    while getopts c:m:d:u:p:h options
    do
        case $options in
            c)  COMMAND="$OPTARG"
                ;;
            m)  JAVA_HOME="$OPTARG"
                ;;
            d)  JAVA_OPTS="$JAVA_OPTS $OPTARG"
                ;;
            u)  RUNNING_USER="$OPTARG"
                ;;
            p)  DEBUG_PORT="$OPTARG"
                ;;
            h)  usage
                exit 1
                ;;
           \?)  usage
                exit 1
                ;;
        esac
    done
    shift $((OPTIND-1))
    if [ ! -z "$1" ] ; then
        ARGS+=($1)
    fi
    shift
done

if [ -z "$JAVA_HOME" ] ; then
    echo "JAVA_HOME not set."
    exit 1
fi

# 判断java命令是否存在
type $JAVA_HOME/bin/java >/dev/null 2>&1 || { echo >&2 "java command not found."; exit 1; }

# 获取进程PID，各应用模块可结合实际情况重写此函数
eval_pid() {
    PID=0
    if [ -n "$(ps -ef | grep "app.name=$APP_NAME" | grep -v grep)" ] ; then
        PID=($(ps -ef | grep "app.name=$APP_NAME" | grep -v grep | awk '{print $2}'))
    fi
}

# 用指定用户身份执行相关命令
execute() {
    cd $BIN_DIR
    if [ ! -z "$RUNNING_USER" ] ; then
        [[ `id -un` = "$RUNNING_USER" ]] && sh -c "$1" || su $RUNNING_USER -c "$1"
    else
        sh -c "$1"
    fi
}

# 以前台进程模式启动
run() {
    eval_pid
    if [ $PID -ne 0 ] ; then
        echo "WARN: $APP_NAME already started (PID=${PID[@]})."
    else
        # 根据实际情况重写启动命令
        execute "$JAVA_HOME/bin/java $JAVA_OPTS -cp $CLASSPATH $MAIN_CLASS"
    fi
}

# 以服务模式启动
start() {
    eval_pid
    if [ $PID -ne 0 ] ; then
        echo "WARN: $APP_NAME already started (PID=${PID[@]})."
    else
        # 根据实际情况重写启动命令，切记不可生成 nohup.out 文件
        execute "nohup $JAVA_HOME/bin/java $JAVA_OPTS -cp $CLASSPATH $MAIN_CLASS > /dev/null 2>&1 &"

        # 判断启动命令是否成功执行
        if [ $? -eq 0 ] ; then
            sleep 3

            # 校验应用是否成功启动
            eval_pid
            if [ $PID -ne 0 ] ; then
                echo "INFO: $APP_NAME started (PID=${PID[@]})."
            else
                echo "ERROR: $APP_NAME start failed."
                exit 1
            fi
        else
            echo "ERROR: $APP_NAME start failed."
            exit 1
        fi
    fi
}

stop() {
    eval_pid
    if [ $PID -ne 0 ] ; then
        echo -n "INFO: Stopping $APP_NAME (PID=${PID[@]}) ..... "
        # 根据实际情况重写停止命令
        execute "kill -9 ${PID[@]}"
        if [ $? -eq 0 ] ; then
            sleep 3
            eval_pid
            if [ $PID -eq 0 ] ; then
                echo "[OK]."
            else
                echo "[Failed]."
                exit 1
            fi
        else
            echo "[Failed]."
            exit 1
        fi
    else
        echo "WARN: $APP_NAME not running."
    fi
}

# 远程debug
debug() {
    eval_pid
    if [ $PID -ne 0 ] ; then
        echo "WARN: $APP_NAME already started (PID=${PID[@]})."
    else
        DEBUG_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=$DEBUG_PORT"
        # 根据实际情况重写启动命令
        execute "$JAVA_HOME/bin/java $JAVA_OPTS $DEBUG_OPTS -cp $CLASSPATH $MAIN_CLASS"
    fi
}

status() {
    eval_pid
    if [ $PID -ne 0 ] ; then
        status_code=`curl --write-out %{http_code} --silent --output /dev/null $STATUS_CHECK_HTTP | sed 's/^ //g;s/ $//g'`
        if [ $? -eq 0 ] && [ $status_code -eq 200 ] ; then
            echo "INFO: $APP_NAME service available."
            exit 0
        else
            echo "ERROR: $APP_NAME service unavailable."
            exit 1
        fi
    else
        echo "WARN: $APP_NAME not running."
        exit 3
    fi
}

if [ -z "$COMMAND" ] ; then
    if [ ${#ARGS[@]} -eq 0 ] ; then
        COMMAND=run
    else
        COMMAND=${ARGS[0]}
    fi
fi

case "$COMMAND" in
    'run')
        run
        ;;
    'start')
        start
        ;;
    'stop')
        stop
        ;;
    'restart')
        stop
        sleep 3
        start
        ;;
    'debug')
        debug
        ;;
    'status')
        status
        ;;
    *)
        exit 1
esac