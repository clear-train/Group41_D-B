import Index from '../components/main.vue'

import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
// import rebot from '@/components/rebot'
// import login from '@/components/user/Login'
import VueRouter from 'vue-router'

Vue.use(Router)
Vue.use(VueRouter);


const originalPush = VueRouter.prototype.push
const originalReplace = VueRouter.prototype.replace
// push
VueRouter.prototype.push = function push (location, onResolve, onReject) {
  if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch(err => err)
}
// replace
VueRouter.prototype.replace = function push (location, onResolve, onReject) {
  if (onResolve || onReject) return originalReplace.call(this, location, onResolve, onReject)
  return originalReplace.call(this, location).catch(err => err)
}
// export default new Router({
//   routes: [
//     {
//       path: '/',
//       name: 'rebot',
//       component: rebot
//     },
//     {
//       path: '/login',
//       name: 'login',
//       component: login
//     }
//   ]
// })

export const loginRouter = {
  path: '/login',
  name: 'login',
  meta: {
      title: 'Login'
  },
  component: resolve => {
      require(['../components/user/login.vue'], resolve)
  }
}

const mainchild = [{
  path: '/',
  title: 'Dashboard',
  name: 'home',
  meta: {
      requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
      title: 'Home'
  },
  component: resolve => {
      require(['../components/home.vue'], resolve)
  }
},]

export const appRouter = 
{
    path: '/',
    name: 'main',
    title: 'Dashboard',
    component: Index,
    redirect: '/home',
    meta: {
        requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
        title: 'Home'
    },
    children:[
      ...mainchild
    ]
}

export const appRouter2 = [
  // 接口分析
  {
      path: '/git',
      icon: 'md-search',
      name: 'git',
      title: 'Equipment Management',
      component: Index,
      children: [{
        path: 'gitinfo',
        icon: 'md-search',
        name: 'gitinfo',
        title: 'Equipment Query',
        meta: {
            title: 'Equipment Query'
        },
        component: resolve => {
            require(['../components/git_check/git_check.vue'], resolve)
        }
      },{
        path:'insert',
        icon:'md-add',
        name:'insert',
        title:'Insert',
        meta: {
          title: 'Insert'
      },
      component: resolve => {
          require(['../components/git_check/insert.vue'], resolve)
      }
      }]
  },{
    path: '/system',
    icon: 'md-settings',
    name: 'system',
    title: 'System',
    component: Index,
    children: [{
        path: 'keyword',
        icon: 'md-key',
        name: 'keyword',
        title: 'Image Acquisition',
        meta: {
            title: 'Image Acquisition'
        },
        component: resolve => {
            require(['../components/system/keyword.vue'], resolve)
        }
    },{
      path: 'cron',
      icon: 'md-time',
      name: 'cron',
      title: 'Userinfo',
      meta: {
          title: 'Userinfo'
      },
      component: resolve => {
          require(['../components/git_check/scheduler.vue'], resolve)
      }
  }
    ]
},
]

export const MainRoute = [
  appRouter,...appRouter2, loginRouter
  // {
  //   path: '/',
  //   name: 'main',
  //   title: 'Dashboard',
  //   component: Index,
  //   redirect: '/home',
  //   meta: {
  //       requireAuth: true, // 添加该字段，表示进入这个路由是需要登录的
  //       title: '首页'
  //   },
  //   children:[
  //     ...mainchild
  //   ]
  // }
//   ,{
//     path: '/report',
//     icon: 'ios-stats',
//     name: 'report',
//     title: '接口分析',
//     component: Index,
//     children:[]
// },
]

const RouterConfig = {
  routes: MainRoute
}

console.log('RouterConfig:',RouterConfig)

export default new Router(RouterConfig)

