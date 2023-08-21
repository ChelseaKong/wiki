import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// add ant design of vue
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
// add icons from ant design of vue / doesn't work
// import * as Icons from '@ant-design/icons-vue';

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

