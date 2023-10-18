// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import axios from 'axios'
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import config from './libs/util'
import { Message } from 'element-ui'
import store from './aspsm'
import { MainRoute, appRouter, appRouter2} from './router/index'
import JsEncrypt from 'jsencrypt'
// iview UI
import iView from 'view-design'
import 'view-design/dist/styles/iview.css'
Vue.use(iView)

Vue.use(ElementUI)
Vue.config.productionTip = false
Vue.prototype.$config = config
Vue.prototype.$message = Message
//JSEncrypt加密方法(参数1:数据，参数2：公钥)
Vue.prototype.$jsEncrypt = function(data,pub_key){
  let encrypt = new JSEncrypt()
  encrypt.setPublicKey(pub_key)
  let en_data = encrypt.encrypt(data)
  return en_data
}

// 路由前业务判断
router.beforeEach((to, from, next) => {
  next()
  
})

router.afterEach(() => {
  // iView.LoadingBar.finish()
  // window.scrollTo(0, 0)
  // document.querySelector("body").setAttribute("style", "overflow: auto!important;")
})

// 请求拦截
axios.interceptors.request.use(request => {
//   let url = request.url
//   var reg = RegExp(/refresh-token/);
//   var reg1 = RegExp(/check-exist/);

  if(sessionStorage.getItem('token')){
//     let token_T = JSON.parse(Base64.decode(sessionStorage.getItem('token').split('.')[1])).exp
//     let now_T = Number(String(parseInt(new Date().getTime())/1000).split('.')[0])
//     if(token_T-now_T <=300 && token_T-now_T>0 ){
//       axios.post(config.url + '/user/refresh-token/',{
//         token:sessionStorage.getItem('token')
//       })
//       .then(res => {
//         if(res.data.code == 0){
//             sessionStorage.setItem("token", res.data.token)
            axios.defaults.headers.common['Authorization'] = 'JWT ' +  sessionStorage.getItem('token')
//         } else {
//           iView.Message.error(res.data.msg)
//         }
//       })
//       .catch(err =>{
//         iView.Message.error(err)
//       })
//     } else if(token_T-now_T<0 ){
//       sessionStorage.clear()
//       return next({ name: 'login' })
//     } 
  }

//   if(router.currentRoute.name == 'login' && router.currentRoute.query.token != undefined ){
//     iView.Message.warning('登录过期，请重新登录')
//     router.push({name: 'login'})
//   }
  return request;
}, err => {
  config.hideLoading();
  return Promise.resolve(err);
});


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store: store,
  components: { App },
  template: '<App/>'
})

