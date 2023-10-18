<style lang="less">
@import "../main.less";
#handleDropdown:hover{
  background: #898181;
  color:#ffffff;
  border-radius: 2px;
  cursor:pointer;
}
#chengePWD:hover{
   background: #898181;
   color:#ffffff;
   border-radius: 2px;
   cursor:pointer;
}
#user_detail:hover{
  background: #898181;
  color:#ffffff;
  border-radius: 2px;
  cursor:pointer;
}
.forget_pwd{
  font-size: 12px;
  color:#2d8cf0;
  margin-top:0px;
  text-align: center;
}
.gray{
  filter: gray; /* IE6-9 */
  filter: grayscale(100%);
  -webkit-filter: grayscale(100%); /* Chrome 19+ & Safari 6+ */
  -moz-filter: grayscale(100%);
  -ms-filter: grayscale(100%);
  -o-filter: grayscale(100%);  
}
</style>
<template>
    
    <div id="main"  class="main" :class="{'main-hide-text': hideMenuText,'gray':graystatus}" >
      <div class="sidebar-menu-con" :style="{width: hideMenuText?'60px':'180px', overflow: hideMenuText ? 'visible' : 'auto', background: '#515a6e'}">
        <div class="logo-con"></div>
        <sidebar-menu v-if="!hideMenuText" :menuList="menuList" :iconSize="18"/>
        <sidebar-menu-shrink v-else :menuList="menuList"/>
      </div>
      <div class="main-header-con" :style="{paddingLeft: hideMenuText?'60px':'170px'}">
        <div style="display:flex" class="main-header">
          <div class="navicon-con">
            <Button :style="{transform: 'rotateZ(' + (this.hideMenuText ? '-90' : '0') + 'deg)'}" type="text" @click="toggleClick">
              <Icon type="md-menu" size="32"></Icon>
            </Button>
          </div>
          <!-- <div class="header-middle-con">
            <div class="main-breadcrumb">
              <breadcrumb-nav :currentPath="currentPath"></breadcrumb-nav>
            </div>
          </div> -->
          <div class="header-avator-con">
            <!-- <div @click="handleFullScreen" v-if="showFullScreenBtn" class="full-screen-btn-con">
              <Tooltip :content="isFullScreen ? '退出全屏' : '全屏'" placement="bottom">
                <Icon :type="isFullScreen ? 'md-contract' : 'md-expand'" :size="23"></Icon>
              </Tooltip>
            </div> -->
            <div class="user-dropdown-menu-con" >
              <Row type="flex" justify="end" align="middle" class="user-dropdown-innercon">
                <Dropdown trigger="custom"  :visible="visible" >
                  <a href="javascript:void(0)" @click="handleOpen">
                    <span class="main-user-name">{{ userName }}</span>
                    <Icon type="md-arrow-dropdown"/>
                  </a>
                  <DropdownMenu slot="list">
                      <div class="dropdown-item" @click="handleDropdown" style="margin: 0px 20px;padding: 5px 4px;" id="handleDropdown">退出登录</div>
                      <!-- <div class="dropdown-item"  style="margin: 0px 20px;padding: 5px 4px;" id="user_detail"><span>个人中心</span> </div> -->
                      <div class="dropdown-item"  id="chengePWD" style="margin: 0px 20px;padding: 5px 4px;">
                        <span @click="handlePw" >修改密码</span>
                        <Modal title="修改密码" v-model="modal8" :mask-closable="false">
                          <Form ref="formCustom" :model="formCustom" :rules="ruleCustom" :label-width="80" label-position="left">
                            <FormItem label="当前密码" prop="old_pwd">
                                <Input type="password" name="olds_pw" v-model="formCustom.old_pwd" ></Input>
                            </FormItem>
                            <FormItem label="新的密码" prop="new_pwd">
                                <Input type="password" v-model="formCustom.new_pwd" required="required"></Input>
                            </FormItem>
                            <FormItem label="确认密码" prop="confirm_pwd">
                                <Input type="password" required="required" v-model="formCustom.confirm_pwd"></Input>
                            </FormItem>
                            <FormItem>
                                <Button type="primary" @click="handleSubmit('formCustom')">提交</Button>
                                <Button @click="handleReset('formCustom')" style="margin-left: 8px">还原</Button>
                            </FormItem>
                            <div class="forget_pwd">忘记密码?请联系管理员</div>
                          </Form>
                          <div slot="footer"></div>
                        </Modal>
                      </div>
                  </DropdownMenu> 
                </Dropdown>
                <Avatar :src="avatorPath" style="background: #ffffff;margin-left: 10px;"></Avatar>
              </Row>
            </div>
          </div>
        </div>
        <div class="tags-con">
          <tags-page-opened :pageTagsList="pageOpenedList"></tags-page-opened>
        </div>
      </div>
      <div class="single-page-con" :style="{paddingLeft: hideMenuText?'60px':'180px'}" >
        <div class="single-page" v-bind:style="{minHeight: Height+'px'}">
          <!-- <keep-alive>
            <router-view v-if="$route.meta.keepAlive"></router-view>
          </keep-alive> -->
          
          <router-view></router-view>
          
        </div>
      </div>
      
    </div>
</template>

<script>
import axios from 'axios'
import sidebarMenu from '../main_components/sidebarMenu.vue'
import tagsPageOpened from '../main_components/tagsPageOpened.vue'
import breadcrumbNav from '../main_components/breadcrumbNav.vue'
import sidebarMenuShrink from '../main_components/sidebarMenuShrink.vue'
import { mapState } from 'vuex'
export default {
  components:{
    sidebarMenu,
    tagsPageOpened,
    breadcrumbNav,
    sidebarMenuShrink
  },
  data () {
    //   验证密码
    var regex = new RegExp(
      "(?=.*[0-9])(?=.*[A-Za-z])(?=.*[^a-zA-Z0-9]).{8,16}"
    );
    const validateCurrent_pw = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入您当前密码'));
            } else if(value!==this.formCustom.old_pwd){
              callback("旧密码填写错误"); 
            }
    }
    const validatePass = (rule, value, callback) => {
        if (value === '') {
            callback(new Error('请输入您新的密码'));
        } else {
          if (!regex.test(value)) {
            callback("密码不符合规范，请设置字母/数字/字符结合方式");
          } else {
            callback();
          }
            
        }
    };
    const validatePassCheck = (rule, value, callback) => {
        if (value === '') {
            callback(new Error('请再次输入您的密码'));
        } else if (value !== this.formCustom.new_pwd) {
            callback(new Error('两次密码输入不一致!'));
        } else {
            callback();
        }
    };
    return {
      hideMenuText: false,
      title:'安全智慧决策和支持平台',
      hideMenuText: false,
      userName: sessionStorage.getItem('user'),
      // realname: sessionStorage.getItem('real_name'),
      showFullScreenBtn: window.navigator.userAgent.indexOf('MSIE') < 0,
      isFullScreen: false,
      lockScreenSize: 0,
      avatorPath: '/rebot/avatar.png',// 初始默认为/logplat/avatar.png
      getc: false,
      statement: false,
      modal8: false,
      modal11: false,
      graystatus:false,
      // 修改密码部分
      formCustom: {
        id:parseInt(sessionStorage.getItem("id")),
        old_pwd:"",  
        new_pwd:"",
        confirm_pwd:'',
      },
      ruleCustom: {
        old_pwd: [
            { validator: validateCurrent_pw, trigger: 'blur' },
            {required: true}
        ],
        new_pwd: [
            { validator: validatePass, trigger: 'blur' }
        ],
        confirm_pwd: [
            { validator: validatePassCheck, trigger: 'blur' }
        ],
      },
      options: [{
          value: 'github',
          label: 'github'
        }, {
          value: 'gitlab',
          label: 'gitlab'
        }, {
          value: 'gitee',
          label: 'gitee'
        }],
        value: '',
        Height:'',
        visible:false //
    }
  },
  methods: {
    
    handlePw(){
      this.publickey()
      this.visible = false
      this.modal8 = !this.modal8
    },
    handleOpen(){
      this.visible = !this.visible
    },
    // 导航栏收缩
    toggleClick () {
      this.hideMenuText = !this.hideMenuText
      this.$store.commit("update_hideMenuText",this.hideMenuText)
    },
    // 个人中心
    logout () {
      localStorage.clear()
      sessionStorage.clear()
      this.$router.push({
        name: 'login'
      })
    },
    // 全屏
    handleFullScreen () {
      let main = document.getElementById('main')
      if (this.isFullScreen) {
        if (document.exitFullscreen) {
          document.exitFullscreen()
        } else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen()
        } else if (document.webkitCancelFullScreen) {
          document.webkitCancelFullScreen()
        } else if (document.msExitFullscreen) {
          document.msExitFullscreen()
        }
      } else {
        if (main.requestFullscreen) {
          main.requestFullscreen()
        } else if (main.mozRequestFullScreen) {
          main.mozRequestFullScreen()
        } else if (main.webkitRequestFullScreen) {
          main.webkitRequestFullScreen()
        } else if (main.msRequestFullscreen) {
          main.msRequestFullscreen()
        }
      }
    },
    // 锁屏
    lockScreen () {
      let lockScreenBack = document.getElementById('lock_screen_back')
      lockScreenBack.style.transition = 'all 3s'
      lockScreenBack.style.zIndex = 10000
      lockScreenBack.style.boxShadow = '0 0 0 ' + this.lockScreenSize + 'px #667aa6 inset'
      this.$store.commit('lock')
      sessionStorage.setItem('last_page_name', this.$route.name) // 本地存储锁屏之前打开的页面以便解锁后打开
      setTimeout(() => {
        lockScreenBack.style.transition = 'all 0s'
        this.$router.push({
          name: 'locking'
        })
      }, 800)
    },
    init () {
      // 全屏相关
      document.addEventListener('fullscreenchange', () => {
        this.isFullScreen = !this.isFullScreen
      })
      document.addEventListener('mozfullscreenchange', () => {
        this.isFullScreen = !this.isFullScreen
      })
      document.addEventListener('webkitfullscreenchange', () => {
        this.isFullScreen = !this.isFullScreen
      })
      document.addEventListener('msfullscreenchange', () => {
        this.isFullScreen = !this.isFullScreen
      })
      // 锁屏相关
      let lockScreenBack = document.getElementById('lock_screen_back')
      let x = document.body.clientWidth
      let y = document.body.clientHeight
      let r = Math.sqrt(x * x + y * y)
      let size = parseInt(r)
      this.lockScreenSize = size
      window.addEventListener('resize', () => {
        let x = document.body.clientWidth
        let y = document.body.clientHeight
        let r = Math.sqrt(x * x + y * y)
        let size = parseInt(r)
        this.lockScreenSize = size
        lockScreenBack.style.transition = 'all 0s'
        lockScreenBack.style.width = lockScreenBack.style.height = size + 'px'
      })
      lockScreenBack.style.width = lockScreenBack.style.height = size + 'px'
      // 问候信息相关
      if (!sessionStorage.getItem('hasGreet')) {
        let now = new Date()
        let hour = now.getHours()
        let greetingWord = {
          title: '',
          words: ''
        }
        let userName = sessionStorage.getItem('user')
        if (hour < 6) {
          greetingWord = {
            title: '凌晨好~' + userName,
            words: '早起的鸟儿有虫吃~'
          }
        } else if (hour >= 6 && hour < 9) {
          greetingWord = {
            title: '早上好~' + userName,
            words: '来一杯咖啡开启美好的一天~'
          }
        } else if (hour >= 9 && hour < 12) {
          greetingWord = {
            title: '上午好~' + userName,
            words: '工作要加油哦~'
          }
        } else if (hour >= 12 && hour < 14) {
          greetingWord = {
            title: '中午好~' + userName,
            words: '午饭要吃饱~'
          }
        } else if (hour >= 14 && hour < 17) {
          greetingWord = {
            title: '下午好~' + userName,
            words: '下午也要活力满满哦~'
          }
        } else if (hour >= 17 && hour < 19) {
          greetingWord = {
            title: '傍晚好~' + userName,
            words: '下班没事问候下爸妈吧~'
          }
        } else if (hour >= 19 && hour < 21) {
          greetingWord = {
            title: '晚上好~' + userName,
            words: '工作之余品一品书香吧~'
          }
        } else {
          greetingWord = {
            title: '深夜好~' + userName,
            words: '夜深了，注意休息哦~'
          }
        }
        this.$Notice.config({
          top: 130
        })
        this.$Notice.info({
          title: greetingWord.title,
          desc: greetingWord.words,
          duration: 4,
          name: 'greeting'
        })
        sessionStorage.setItem('hasGreet', 1)
      }
    },
    statementput () {
      axios.put(`${this.$config.url}/homedata/statement`)
    },
    // 修改密码
    handleSubmit (id) {
      axios.post(`${this.$config.url}/users/mod-pwd`, this.formData)
      .then(res => {
            if(res.data.code==1001){
            this.$Message.error("旧密码填写错误");
          }else if(res.data.code==1002){
            this.$Message.error("两次输入密码的不一致");
          }else if(res.data.code==200){
            this.$Message.success("密码修改成功，请重新登录");
            this.$router.push({path: '/login'})
          }
      })
      .catch(error => {
        this.$config.err_notice(this, error);
      });
    },
    handleReset (name) {
        this.$refs[name].resetFields();
    },
    // 密码加密
    publickey() {
      axios.get(`${this.$config.url}/users/pub_key`)
        .then(res => {
            if(res.data.code == 200){
                this.pub_key = res.data.data["pub_key"];
            }
        })
        .catch(error => {
            this.$Message.error('获取公钥异常')
        });
    },

    // 测试
    handleDropdown(){
      this.visible = false
      axios.get(`${this.$config.url}/users/logout`)
      .then (res => {
        if(res.data.code == 200){
          this.logout()
        } else {
          this.$Message.error(res.data.msg)
        }
      })
      .catch (error => {
        console.log('error')
      })   
    },
  },
  mounted () {
      this.$store.state.currentPageName = this.$route.matched[1].name
      if (localStorage.getItem('pageOpenedList')) {
        this.$store.state.pageOpenedList = JSON.parse(localStorage.getItem('pageOpenedList'))
      } else {
        this.$store.state.pageOpenedList = [{
          title: '首页',
          path: '',
          name: 'home'
        }]
      }
      this.init()


    //动态设置内容高度 让footer始终居底   header+footer的高度是100
    this.Height = document.documentElement.clientHeight - 200;  
    //监听浏览器窗口变化　
    window.onresize = ()=> {this.Height = document.documentElement.clientHeight -200}

     // 关闭浏览器窗口的时候清空浏览器缓存在localStorage的数据
    window.onbeforeunload = function (e) {
    var storage = window.localStorage;
      storage.clear()
    }
  },
  computed: {
    ...mapState([
      'pageOpenedList',
      'currentPath',
      'menuList'
    ]),
    formData() {
      let pubkey = this.pub_key; //公钥，pkcs#1格式，字符串
      let jsencrypt = new JSEncrypt(); //加密对象
      jsencrypt.setPublicKey(pubkey); // 设置密钥
      this.formCustom.old_pwd = jsencrypt.encrypt(this.formCustom.old_pwd.toString());
      this.formCustom.new_pwd = jsencrypt.encrypt(this.formCustom.new_pwd.toString());
      this.formCustom.confirm_pwd = jsencrypt.encrypt(this.formCustom.confirm_pwd.toString());
      return{
        id:parseInt(sessionStorage.getItem("id")),
        old_pwd:this.formCustom.old_pwd,  
        new_pwd:this.formCustom.new_pwd,
        confirm_pwd:this.formCustom.confirm_pwd,
      }
    },
    //侧边栏状态
    asidestatus(){
      return this.$store.state.asidestatus
    },
    echarts_data(){
      return this.$store.state.echarts_data
    }
  },
  created () {
    // 权限菜单过滤相关
    this.$store.commit('Menulist')
    
  },

}
</script>


