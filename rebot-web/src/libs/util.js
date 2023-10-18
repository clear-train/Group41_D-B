import { appRouter2 } from '../router'

let util = { websock: null }

// util.url = location.protocol + '//' + document.domain + ':29943/rebotapi/v1'
// util.url = location.protocol + '//' + '192.168.36.77:20220/logapi/v1'
// util.url = location.protocol + '//' + '10.100.40.93:9898/rebotapi/v1'
util.url = '/apis'

util.requireList = {  //加载组件
    "main": resolve => require(['../components/main.vue'], resolve),
    "home": resolve => require(['../components/home.vue'], resolve),
    "report": resolve => require(['../components/main.vue'], resolve),
    "app-report": resolve => require(['../components/git_check/git_check.vue'], resolve),
    "insert":resolve => require(['../components/git_check/insert.vue'], resolve),
    "system": resolve => require(['../components/main.vue'], resolve),
    "keyword": resolve => require(['../components/system/keyword.vue'], resolve),
}

util.openPage = function(vm, name) {
    vm.$router.push({ name: name })
    vm.$store.commit('Breadcrumbset', name)
    vm.$store.state.currentPageName = name
    util.taglist(vm, name)
}

util.taglist = function(vm, name) {
    vm.$store.state.pageOpenedList.forEach((vl, index) => {
        if (vl.name === name && name !== 'home') {
            vm.$store.state.pageOpenedList.splice(index, 1)
        }
    })
    appRouter2.forEach((val) => {
        for (let i of val.children) {
            if (i.name === name && name !== 'home') {
                vm.$store.state.pageOpenedList.push({ 'title': i.title, 'name': i.name })
            }
        }
    })
    localStorage.setItem('pageOpenedList', JSON.stringify(vm.$store.state.pageOpenedList))
}

export default util
