<style>
.Menu span {
  color: #ffffff;
  font-weight: bold;
  margin-left: 10%;
}

.MenuIcon {
  margin-left: 10%;
  margin-top: 0.1%;
  color: #5cadff;
}
.test3 /deep/ .ivu-menu-submenu-title{
  padding: 12px 23px;
}
.test3 /deep/ .ivu-menu-item{
  padding: 11px 24px;
}

</style>
<template>
  <Menu style="width:auto"  theme="dark" @on-select="currentPageTab" accordion class="test1">
    <MenuItem name="main" class="test2">
      <br>
    </MenuItem>
    <MenuItem name="home">
      <Icon type="md-home" :size="iconSize"></Icon>
      <span class="layout-text">Dashboard</span>
    </MenuItem>
    <template v-for="item in menuList">
      <Submenu v-if="item.children.length>=1 && item.name !== 'main'" :name="item.name" :key="item.path"  class="test3">
        <!-- 侧边栏一级菜单文字 -->
        <template slot="title" class="test4">
          <Icon :type="item.icon" :size="iconSize"></Icon>
          <span class="layout-text">{{ item.title }}</span>
        </template>
        <!-- 二级菜单 -->
        <template v-for="child in item.children">
          <template v-if="filtermenulist[child.name] === '1'">
            <!-- 循环路由 -->
            <MenuItem :name="child.name" :key="child.name" style="margin-left: -5%">
              <Icon :type="child.icon" :size="iconSize" :key="child.name"></Icon>
              <span class="layout-text" :key="child.name + 1">{{ child.title }}</span>
            </MenuItem>
          </template>

          <!-- 第三方告警 -->
          <template v-else-if="filtermenulist[child.name] === 'grafana'">
            <!-- 循环路由 -->
            <MenuItem :name="child.name" :key="child.name" style="margin-left: -5%" @click.native="grafana">
              <Icon :type="child.icon" :size="iconSize" :key="child.name"></Icon> 
              <span class="layout-text" :key="child.name + 1">{{ child.title }}</span>
            </MenuItem>
          </template>
        </template>
      </Submenu>
    </template>
    <Menu-item name="#" @click.native="logout">
      <Icon type="md-log-out" :size="iconSize"></Icon>
      <span class="layout-text">Log out</span>
    </Menu-item>
  </Menu>
</template>
<script>
//
import util from "../libs/util"
import axios from "axios"

  export default {
    name: 'sidebarMenu',
    props: {
      menuList: Array,
      iconSize: Number
    },
    data () {
      return {
        filtermenulist: {
          'git': '1',
          'gitinfo': '1',
          'insert':'1',
          'system': '1',
          'keyword': '1',
          'cron': '1',
        },
      }
    },
    computed: {
      currentPageName () {
        return this.$store.state.currentPageName;
      }
    },
    methods: {
      currentPageTab(val) {
        if (val === "login") {
          localStorage.clear();
          sessionStorage.clear();
          this.$router.push({
            name: "login"
          });
        } else {
          util.openPage(this, val);
        }
      },
      logout(){
        axios.get(`${this.$config.url}/users/logout`)
        .then (res => {
          if(res.data.code == 200){
            localStorage.clear()
            sessionStorage.clear()
            this.$router.push({
              name: 'login'
            })
          } else {
            Message.error(res.data.msg)
          }
          
        })
        .catch (error => {
          console.log('error')
        })
      },
      grafana(){
        window.open('https://efin-monitor.95598pay.com/')
      },
    }
  }
  
</script>
