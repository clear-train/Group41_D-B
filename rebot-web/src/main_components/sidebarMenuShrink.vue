<style>

  .btn_hover {
    width: 60px;
    margin-left: 30%;
    padding: 10px 0;
  }

</style>

<template>
  <div >
    <template v-for="(item, index) in menuList">
      <Dropdown v-if="item.children.length >= 1 && item.name != 'main'" placement="right-start" :key="index"
                @on-click="changeMenu">
        <div class="btn_hover">
          <Icon :size="20" :color="iconColor" :type="item.icon"></Icon>
        </div>
        <DropdownMenu style="width: 200px;" slot="list">
          <template v-for="child in item.children">
            <template v-if="filtermenulist[child.name] === '1'">
              <DropdownItem :name="child.name" :key="child.title">
                <Icon :type="child.icon" size="20"></Icon>
                <span style="padding-left:10px;">{{ child.title }}</span>
              </DropdownItem>
            </template>
          </template>
        </DropdownMenu>
      </Dropdown>
      <Dropdown v-else placement="right-start" :key="index" @on-click="changeMenu">
        <div @click="changeMenu(item.children[0].name)" class="btn_hover">
          <Icon :size="20" :color="iconColor" :type="item.icon"></Icon>
        </div>
        <DropdownMenu style="width: 200px;" slot="list">
          <DropdownItem :name="item.name" :key="item.title">
            <Icon :type="item.icon" size="20"></Icon>
            <span style="padding-left:10px;">{{ item.title }}</span>
          </DropdownItem>
        </DropdownMenu>
      </Dropdown>
    </template>
    <Dropdown placement="right-start" @on-click="changeMenu">
      <div @click="changeMenu('login')" class="btn_hover">
        <Icon type="md-log-out" size="20" :color="iconColor"></Icon>
      </div>
      <DropdownMenu slot="list">
        <DropdownItem name="login" key="login">退出</DropdownItem>
      </DropdownMenu>
    </Dropdown>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'sidebarMenuShrink',
    props: {
      menuList: {
        type: Array
      },
      iconColor: {
        type: String,
        default: 'white'
      }
    },
    data () {
      return {
        currentPageName: this.$route.name,
        openedSubmenuArr: this.$store.state.openedSubmenuArr,
        filtermenulist: {
          'git': '1',
          'gitinfo': '1',
          'insert':'1',
          'system': '1',
          'keyword': '1',
          'cron': '1',
        }
      }
    },
    methods: {
      changeMenu (active) {
        if (active === 'login') {
          localStorage.clear()
          sessionStorage.clear()
          this.$router.push({
            name: active
          })
        } else {
          this.$config.openPage(this, active)
        }
      }
    },
  }
</script>
