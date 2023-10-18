<style lang="less">
  @import './unlock.less';
</style>

<template>
  <transition name="show-unlock">
    <div class="unlock-body-con" v-if="showUnlock" @keydown.enter="handleUnlock">
      <div @click="handleClickAvator" class="unlock-avator-con" :style="{marginLeft: avatorLeft}">
        <img class="unlock-avator-img" src="../assets/avatar.png">
        <div class="unlock-avator-cover">
          <span><Icon type="md-unlock" :size="30"></Icon></span>
          <p>解锁</p>
        </div>
      </div>
      <div class="unlock-avator-under-back" :style="{marginLeft: avatorLeft}"></div>
      <div class="unlock-input-con">
        <div class="unlock-input-overflow-con">
          <div class="unlock-overflow-body" :style="{right: inputLeft}">
            <input ref="inputEle" v-model="password" class="unlock-input" type="password" placeholder="密码同登录密码"/>
            <button ref="unlockBtn" @mousedown="unlockMousedown" @mouseup="unlockMouseup" @click="handleUnlock"
                    class="unlock-btn">
              <Icon color="white" type="md-key" :size="14"></Icon>
            </button>
          </div>
        </div>
      </div>
      <div class="unlock-locking-tip-con">已锁定</div>
    </div>
  </transition>
</template>

<script>
  // ;
  import axios from 'axios'
  import util from '../libs/util'
  import Vue from "vue";
let Base64 = require("js-base64").Base64;
// import SIdentify from "../identifys/identify";
import CryptoJS from "crypto-js/crypto-js";
import { encode } from "punycode";
  export default {
    name: 'Unlock',
    data () {
      return {
        avatorLeft: '0px',
        inputLeft: '400px',
        password: ''
      }
    },
    props: {
      showUnlock: {
        type: Boolean,
        default: false
      }
    },
    methods: {
        publickey() {
            axios.get(`${this.$config.url}/login`)
            .then(res => {
            this.pub_key = res.data["pub_key"];
            this.pub_key = Base64.decode(this.pub_key);
            })
            .catch(error => {
            this.$config.err_notice(this, "系统出了点问题，请稍后重试...");
            console.log(error);
            });
        },
        handleClickAvator () {
            this.avatorLeft = '-180px'
            this.inputLeft = '0px'
            this.$refs.inputEle.focus()
        },
        handleUnlock () {
            this.$Message.destroy()
            let pubkey = this.pub_key; //公钥，pkcs#1格式，字符串
            let jsencrypt = new JSEncrypt(); //加密对象
            jsencrypt.setPublicKey(pubkey); // 设置密钥
            let en_pwd = jsencrypt.encrypt(this.password); //加密
            let count = window.crypto.getRandomValues(new Uint32Array(1))[0];
            axios.defaults.headers.common["sid"] = count;
            axios.post(`${this.$config.url}/user/login/`, {
                'username': sessionStorage.getItem('user'),
                password: en_pwd
            })
            .then(res => {
                if (res.data.code  === 0) {
                    this.avatorLeft = '0px'
                    this.inputLeft = '400px'
                    this.password = ''
                    this.$store.commit('unlock')
                    this.$emit('on-unlock')
                    
                } else {
                    this.$Message.error(res.data.msg)
                }
            })
            .catch(error => {
                this.$Message.error('认证失败，请重新输入')
            })
        },
        unlockMousedown () {
            this.$refs.unlockBtn.className = 'unlock-btn click-unlock-btn'
        },
        unlockMouseup () {
            this.$refs.unlockBtn.className = 'unlock-btn'
        }
    },
    created: function() {
        this.publickey()
    }
  }
</script>