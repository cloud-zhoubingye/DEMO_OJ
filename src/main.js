import { createApp } from 'vue'
import ViewUIPlus from 'view-ui-plus'
import App from './App.vue'
import router from './router'
import store from './store'
import locale from 'view-ui-plus/dist/locale/en-US';
// import './styles/index.less'
import '../my-theme/index.less';
// import './mock'

const app = createApp(App)

app.use(store)
  .use(router)
  .use(ViewUIPlus)
  .mount('#app')

// 使用英文界面
app.use(ViewUIPlus, {
    locale
});
