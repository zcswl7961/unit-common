--
--version 1.0.0,build 2018-10-20
-- 数据全流程，系统版本信息
--import#dataflow_create.sql#1.0.0,build 2018-10-20#1.0.0,build 2018-10-20

--
--version 1.0.0,build 2018-10-25
-- 成都淞幸公司，气象元数据表建立
--import#dataflow_create.sql#1.0.0,build 2018-10-25#1.0.0,build 2018-10-25

--
--version 1.0.0,build 2018-10-31
-- 数据全流程表初始化建立。
--import#dataflow_create.sql#1.0.0,build 2018-10-31#1.0.0,build 2018-10-31
-- 数据全流程表数据初始化
--import#dataflow_data.sql#1.0.0,build 2018-10-31#1.0.0,build 2018-10-31

--
--version 1.0.0,build 2019-04-09
-- 数据全流程开发周期是2018-06月开始，到2019-04月才有第一个最终固化版本版本
-- 为了让试点已经安装的省份和未来安装的省份数据库版本统一，所以这边直接跳到2019年4月版本
-- 数据全流程表数据初始化，这个表数据比较大，所以跟其他表分开两个文件进行数据初始化
--import#wolf_area.sql#1.0.0,build 2019-04-09#1.0.0,build 2019-04-09

