import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// add ant design of vue
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
// add icons from ant design of vue / doesn't work
// import * as Icons from '@ant-design/icons-vue';
// axios
import axios from "axios";

axios.defaults.baseURL = process.env.VUE_APP_SERVER;

// axios拦截器
axios.interceptors.request.use(function (config) {
    console.log('Request Argu: ', config);
    return config;
}, error => {
    return Promise.reject(error);
});
axios.interceptors.response.use(function (response) {
    console.log('Return Argu: ', response);
    return response;
}, error => {
    console.log('Return ERROR: ', error);
    return Promise.reject(error);
})

const app = createApp(App);
app.use(store).use(router).use(Antd).mount('#app');

// 全局使用icons
//const icons: any = Icons;
//for (const i in icons) {
//    app.component(i, icons[i]);
//}

// 多环境测试
console.log('ENV: ', process.env.NODE_ENV);
console.log('SERVER: ', process.env.VUE_APP_SERVER);

