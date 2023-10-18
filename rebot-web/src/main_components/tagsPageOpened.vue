<style lang="less">
  @import '../main.less';
</style>
<template>
  <div style="display:flex">
    <div >
      <transition-group name="taglist-moving-animation">
        <Tag type="dot" @on-close="closePage" :key="this.pageTagsList[this.pageTagsList.length-1].title">
          {{ this.pageTagsList[this.pageTagsList.length-1].title }}
        </Tag>
      </transition-group>
    </div>
  </div>
  
</template>

<script>
  export default {
    name: 'tagsPageOpened',
    data () {
      return {
        currentPageName: this.$route.name,
        tagBodyLeft: 0
      }
    },
    props: {
      pageTagsList: Array
    },
    computed: {
      title () {
        return this.$store.state.currentTitle
      }
    },
    methods: {
      closePage (event, name) {
        this.$store.commit('removeTag', name)
        if (this.currentPageName === name) {
          let lastPageName = ''
          if (this.$store.state.pageOpenedList.length > 1) {
            lastPageName = this.$store.state.pageOpenedList[1].name
          } else {
            lastPageName = this.$store.state.pageOpenedList[0].name
          }
          this.$router.push({
            name: lastPageName
          })
          this.$store.commit('Breadcrumbset', lastPageName)
          this.$store.state.currentPageName = lastPageName
        }
      },
      linkTo (name) {
        this.$router.push({
          name: name
        })
        this.$store.commit('Breadcrumbset', name)
        this.$store.state.currentPageName = name
      },
      handleTagsOption (type) {
        if (type === 'clearAll') {
          this.$store.commit('clearAllTags')
          this.$router.push({
            name: 'home'
          })
        } else {
          this.$store.commit('clearOtherTags', this)
        }
        this.tagBodyLeft = 0
      }
    },
    watch: {
      '$route' (to) {
        this.currentPageName = to.name
      }
    },
  }
</script>
