import React, { Component } from 'react';
import { BrowserRouter, Route, Redirect, Switch } from 'react-router-dom';
import './modules/assets/fonts/iconfont.css';
import './App.css';
import 'semantic-ui-css/semantic.min.css';
import Main from './modules/main.js';
import Login from './Login';
import Show from './Show';
import axios from 'axios';
import config from './common.js';
import House from './modules/home/list';

//设置全局的  axios baseUrl 配置
axios.defaults.baseURL = config.apiBaseUrl;
//设置拦截器
axios.interceptors.request.use(function (config) {
  //在发送请求前获取mytoken的值
  if(!config.url.endsWith('/login')){
    config.headers.Authorization = localStorage.getItem('mytoken');
  }
  return config;
}, function (error) {
  //获取数据失败处理
  return Promise.reject(error);
});
axios.interceptors.response.use(function (response) {
  // 对响应的拦截——————返回response.data数据
  return response.data;
}, function (error) {
  return Promise.reject(error);
});

class App extends Component {
  render() {
    return (
      <BrowserRouter>
        <Switch>
          <Route exact path="/" component={Login}/>
          <Route path="/show" component={Show}/>
          <Route path="/home" component={Main}/>
          <Route path="/abc" component={House} />
          <Redirect to="/"/>
        </Switch>
      </BrowserRouter>
    );
  }
}

export default App;
