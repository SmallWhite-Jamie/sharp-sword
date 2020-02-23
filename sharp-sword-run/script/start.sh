#!/bin/bash
#这里可替换为你自己的执行程序，其他代码无需更改
APP_NAME=sharp-sword.jar
APP_HOME=`pwd`
#log文件夹不存在就创建
if [[ ! -d "${APP_HOME}/log" ]];then
  mkdir ${APP_HOME}/log
fi
LOG_PATH=${APP_HOME}/log/${APP_NAME}.log
#使用说明，用来提示输入参数
usage() {
    echo "Usage: sh start.sh [start|stop|restart|status]"
    exit 1
}

#检查程序是否在运行
is_exist(){
  # awk '{print $2}' 提取输入的第二列,即PID列
  pid=`ps -ef|grep ${APP_NAME}|grep -v grep|awk '{print $2}' `
  #如果不存在返回1，存在返回0
  if [[ -z "${pid}" ]]; then
   return 1
  else
    return 0
  fi
}

#启动方法
start(){
  is_exist
  if [[ $? -eq "0" ]]; then
    echo "${APP_NAME} is already running. pid=${pid} ."
  else
    nohup java -jar ${APP_NAME} > ${LOG_PATH} 2>&1 &
    echo "-----------------------------------------"
    echo "-----正在启动,可按CTRL+C退出当前界面-----"
    echo "-----------------------------------------"
    sleep 1s
    tail -f ${LOG_PATH}
  fi
}

#停止方法
stop(){
  is_exist
  if [[ $? -eq "0" ]]; then
    kill -9 ${pid}
    echo "${APP_NAME} stop [ ok ]"
  else
    echo "${APP_NAME} is not running"
  fi
}

#输出运行状态
status(){
  is_exist
  if [[ $? -eq "0" ]]; then
    echo "${APP_NAME} is running. Pid is ${pid}"
  else
    echo "${APP_NAME} is not running."
  fi
}

#重启
restart(){
  stop
  start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac