<template>
        <div>
            <div>
            <label>Equipment Number:</label>
            <input v-model="equipmentNum" type="text" />

            <label>Equipment Name:</label>
            <input v-model="useBy" type="text" />

            <label>User:</label>
            <input v-model="belongTo" type="text" />

    <label>Equipment Status:</label>
    <input v-model="equipmentStatus" type="radio" name="equipmentStatus" value=1 > Run
    <input v-model="equipmentStatus" type="radio" name="equipmentStatus" value=2 > Stop
    <label>Exploring Treasures:</label>
    <input v-model="networkStatus" type="radio" name="networkStatus" value=1 > Book
    <input v-model="networkStatus" type="radio" name="networkStatus" value=2 > Key
    <input v-model="networkStatus" type="radio" name="networkStatus" value=3 > Rubik's cube

            <button @click="filterTable">Screen</button>
            </div>

            <div>
                <Table :columns="tableColumns" :data="tableData" var="l"></Table>

            </div>

        <div style="text-align: center;">
        <el-pagination background layout="prev, pager, next" @current-change="pageChange" :page-size="pageSize"
       :total="account" :current-page="pageIndex" />
        </div>

   </div>

</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      // 筛选条件
      equipmentNum: '',
      useBy: '',
      belongTo: '',
      equipmentStatus: '',
      networkStatus: '',
      tableData: [], // 获取表格数据

      tableColumns: [
        {
          title: 'Record Id',
          key: 'equipmentId'
        },
        {
          title: 'Equipment Number',
          key: 'equipmentNum'
        },
        {
          title: 'User ID',
          key: 'serialNum'
        }, {
          title: 'Equipment Status',
          key: 'equipmentStatus'
        },
        {
          title: 'Exploring Treasures',
          key: 'networkStatus'
        },
        {
          title: 'Describe',
          key: 'refer'
        },
        {
          title: 'User',
          key: 'belongTo'
        },
        {
          title: 'Equipment Name',
          key: 'useBy'
        },
        {
          title: 'Start Use',
          key: 'startUse'
        },
        {
          title: 'Exploration duration',
          key: 'brand'
        },
        {
          title: 'Number of Treasures',
          key: 'specification'
        },
        {
          title: 'Photo',
          key: 'component'
        },
        {
          title: 'Location',
          key: 'costCenter'
        },
        {
          title: 'View Camera',
          key: 'sn'
        },
        {
          title: 'Map',
          key: 'centerAddress'
        },
        {
          title: 'IP',
          key: 'ip'
        },
        {
          title: 'Others',
          key: 'operatingSystem'
        },
        {
          title: 'Create Time',
          key: 'createTime'
        },
        {
          title: 'Update Time',
          key: 'updateTime'
        }],
      account: 0,
      pageSize: 10, // 每页几行
      pageIndex: 1, // 起始页
      ruledata: [],
      git_type: [
        {
          value: 'github',
          label: 'github'
        }, {
          value: 'gitlab',
          label: 'gitlab'
        }, {
          value: 'gitee',
          label: 'gitee'
        }
      ],
      value: '',
      Height: '',
      visible: false
    }
  },

  mounted () {
    this.pageChange()
  },

  methods: {
    filterTable () {
      this.pageIndex = 1 // 重置分页索引为第一页
      this.pageChange()
    },

    pageChange (pageIndex) {
      if (pageIndex) this.pageIndex = pageIndex
      const params = {
        pageSize: this.pageSize,
        pageIndex: this.pageIndex,
        equipmentNum: this.equipmentNum || null,
        useBy: this.useBy || null,
        belongTo: this.belongTo || null,
        equipmentStatus: this.equipmentStatus || null,
        networkStatus: this.networkStatus || null
      }

      axios
        .get(`${this.$config.url}/equipment/search`, {
          params
        })
        .then((res) => {
          this.tableData = res.data.equipmentList
          this.account = res.data.account
        })
        .catch((error) => {
          console.log(error)
        })
    },

    Getdata () {
      if (this.value !== '') {
        if (this.value === 'github') {
          this.GithubList()
        } else if (this.value === 'gitlab') {
          this.GitlabList()
        } else if (this.value === 'gitee') {
          this.GiteeList()
        }
      }
    },
    // github 获取
    GithubList () {
      axios.get(`${this.$config.url}/github/repo_list`, {
        params: {
          'q': '95598pay'
        }
      })
        .then(res => {
          this.tableData = res.data.data.github
        })
        .catch(error => {
          console.log(error)
        })
    },
    // 触发开启定时任务
    Cronstart () {
      axios.post(`${this.$config.url}/git/trigger_start`)
        .then(res => {
          if (res.data.code === 0) {
            this.$message.success(res.data.msg)
          }
        })
        .catch(error => {
          console.log(error)
        })
    },
    // 规则列表
    rule_list () {
      axios.get(`${this.$config.url}/git/rule_list`)
        .then(res => {
          console.log(res.data)
          this.ruledata = res.data.data
        })
        .catch(error => {
          console.log(error)
        })
    },
    // gitee 获取
    GiteeList () {
      axios.get(`${this.$config.url}/gitee/repo_list`, {
        params: {
          'q': 'sgec'
        }
      })
        .then(res => {
          this.tableData = res.data.data
        })
        .catch(error => {
          console.log(error)
        })
    },
    // gitlab 获取
    GitlabList () {
      axios.get(`${this.$config.url}/gitlab/repo_list`, {
        params: {
          'search': 'sgec'
        }
      })
        .then(res => {
          this.tableData = res.data.data
        })
        .catch(error => {
          console.log(error)
        })
    },
    // git扫描信息获取
    gitinfo () {
      axios.get(`${this.$config.url}/git/gitinfo`)
        .then(res => {
          this.tableData = res.data.data
        })
        .catch(error => {
          console.log(error)
        })
    }
  }

}

</script>
