import Vue from 'vue'
import Vuex from 'vuex'
import {
  MainRoute,
  appRouter,
  appRouter2
} from './router/index'
import util from './libs/util'
import Index from './components/main.vue'

Vue.use(Vuex)

const store = new Vuex.Store({

  state: {
    initRoute: false,
    // appRouter: true,
    // 数据库登录信息
    formItem: {
      input: '',
      sqladdress: '',
      sqlserver: '',
      basename: '',
      table_name: '',
      select: '',
      date: '',

    },
    // 新的属性
    menuList: [],
    routers: [
      MainRoute,
    ],
    // routers: MainRoute,
    currentPageName: 'home',
    currentPath: [],
    pageOpenedList: [{
      title: '首页',
      path: '',
      name: 'home'
    }], 
    tagsList: [...appRouter2],
    cachePage: [],
    asidestatus:false,
    echarts_data:[],
    hideMenuText:false, //侧边菜单栏
    nginx_monitor_status:false,
  },
  mutations: {

    setIsSuperuser(state, val) {
      state.isSuperuser = val
    },
    clearAllTags(state) {
      state.pageOpenedList.splice(1)
      state.cachePage.length = 0
      localStorage.pageOpenedList = JSON.stringify(state.pageOpenedList)
    },
    clearOtherTags(state, vm) {
      let currentName = vm.$route.name
      let currentIndex = 0
      state.pageOpenedList.forEach((item, index) => {
        if (item.name === currentName) {
          currentIndex = index
        }
      })
      if (currentIndex === 0) {
        state.pageOpenedList.splice(1)
      } else {
        state.pageOpenedList.splice(currentIndex + 1)
        state.pageOpenedList.splice(1, currentIndex - 1)
      }
      let newCachepage = state.cachePage.filter(item => {
        return item === currentName
      })
      state.cachePage = newCachepage
      localStorage.pageOpenedList = JSON.stringify(state.pageOpenedList)
    },
    Menulist(state) {
      let accessCode = parseInt(sessionStorage.getItem('access')) // 0
      let menuList = []
      appRouter2.forEach((item, index) => {
        if (item.access !== undefined) { // item.access=0
          if (util.showThisRoute(item.access, accessCode)) {
            if (item.children.length <= 1) {
              menuList.push(item)
            } else {
              let i = menuList.push(item)
              let childrenArr = []
              childrenArr = item.children.filter(child => {
                if (child.access !== undefined) {
                  if (child.access === accessCode) {
                    return child
                  }
                } else {
                  return child
                }
              })
              menuList[i - 1].children = childrenArr
            }
          }
        } else {
          if (item.children.length <= 1) {
            menuList.push(item)
          } else {
            let i = menuList.push(item)
            let childrenArr = []
            childrenArr = item.children.filter(child => {
              if (child.access !== undefined) {
                if (util.showThisRoute(child.access, accessCode)) {
                  return child
                }
              } else {
                return child
              }
            })
            menuList[i - 1].children = childrenArr
          }
        }
      })
      state.menuList = menuList
    },
    lock() {
      sessionStorage.setItem('locking', '1')
    },
    unlock() {
      sessionStorage.setItem('locking', '0')
    },
    Breadcrumbset(state, name) {
      if (name === 'ownspace_index') {
        state.currentPath.splice(1, state.currentPath.length - 1)
        state.currentPath.push({
          'title': '个人中心',
          'path': 'ownspace',
          'name': name
        })
      } else if (name === 'home_index') {
        state.currentPath.splice(1, state.currentPath.length - 1)
      } else if (name === 'myorder') {
        state.currentPath.splice(1, state.currentPath.length - 1)
        state.currentPath.push({
          'title': '我的工单',
          'path': 'message',
          'name': name
        })
      } else {
        state.currentPath.splice(1, state.currentPath.length - 1)
        appRouter2.forEach((val) => {
          for (let i of val.children) {
            if (i.name === name) {
              state.currentPath.push({
                'title': val.title,
                'path': val.path,
                'name': val.name
              }, {
                'title': i.title,
                'path': `${val.path}/${i.path}`,
                'name': i.name
              })
            }
          }
        })
      }
    },
    removeTag(state, name) {
      state.pageOpenedList.map((item, index) => {
        if (item.name === name) {
          state.pageOpenedList.splice(index, 1)
        }
      })
    },
    updatestatus(state,status){
      state.asidestatus = status
    },
    update_echarts_data(state,data,i){
      if(data == ''){
        state.echarts_data.splice(i,1)
      } else {
        state.echarts_data = data
      }
      
    },
    update_hideMenuText(state,status){
      state.hideMenuText = status
    },
  },
  actions: {

    handleAddRoutes(context) {

    }
  }
})

export default store
