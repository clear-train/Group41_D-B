<style scoped>
.el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
  .scheduler{
    display: flex;
    margin-top: 1%;
    margin-left: 1%;
  }
</style>
<template>
    <div>
        <!-- <el-select v-model="value" placeholder="请选择">
        <el-option
          v-for="item in git_type"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select> -->
      <!-- <el-button @click="Getdata">获取规则</el-button>
      <el-button @click="GithubList">点击请求</el-button> -->
      <!-- <el-button @click="Cronstart">开启定时任务</el-button> -->
      <!-- <el-table
        :data="tableData"
        style="width: 100%">
        <el-table-column
          prop="id"
          label="id"
          width="180">
        </el-table-column>
        <el-table-column
          prop="name"
          label="name"
          width="180">
        </el-table-column>
        <el-table-column
          prop="full_name"
          label="full_name">
        </el-table-column>
        <el-table-column
          prop="login"
          label="login">
        </el-table-column>
        <el-table-column
          prop="html_url"
          label="html_url">
        </el-table-column>
      </el-table> -->
      <el-button v-if="job_status" @click="scheduler_change(true)" class="scheduler">启动</el-button>
      <el-button v-else @click="scheduler_change(false)" class="scheduler">关闭</el-button>

      <!-- <el-button v-if="scheduler_status" :disabled="scheduler_button" >暂停</el-button>
      <el-button v-else :disabled="scheduler_button">恢复</el-button>    -->
      <el-table
        :data="ruledata"
        style="width: 100%;margin-top: 1%;">
        <el-table-column
          prop="id"
          label="id"
          width="180">
        </el-table-column>
        <el-table-column
          prop="name"
          label="name"
          width="180">
        </el-table-column>
        <el-table-column
          prop="executor"
          label="executor"
          width="180">
        </el-table-column>
        <el-table-column
          prop="max_instances"
          label="max_instances"
          width="180">
        </el-table-column>
      </el-table>
    </div>
</template>
<script>
import axios from 'axios'
export default {
  data () {
    return {
      scheduler_status: true,
      job_status: null,
      scheduler_button: true,
      tableData: [],
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
      visible: false //
    }
  },
  methods: {
    //
    Getdata () {
      if (this.value !== '') {
        if(this.value == 'github'){
                this.GithubList()
                }else if(this.value == 'gitlab'){
                this.GitlabList()
                }else if(this.value == 'gitee'){
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
                console.log(this.tableData)
            })
            .catch(error => {
                console.log(error)
            })
        },
        //定时任务开启/暂停
        scheduler_change (val) {
            this.job_status = !this.job_status
            axios.post(`${this.$config.url}/git/trigger_start`,{
                'status': val
            })
            .then(res => {
                if(res.data.code == 1000){
                    this.scheduler_button = !this.scheduler_button
                    this.$message.success(res.data.msg)
                } else if(res.data.code == 1001){
                    this.scheduler_button = true
                } else if(res.data.code == 0){
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
                if(res.data.code == 0){
                    this.ruledata = res.data.data
                    if(res.data.job_status == 0){
                        this.job_status = true
                    } else {
                        this.job_status = false
                    }
                }
                
            })
            .catch(error => {
                console.log(error)
            })
        },
        // gitee 获取
        GiteeList(){
            axios.get(`${this.$config.url}/gitee/repo_list`, {
                params: {
                'q': 'sgec'
                }
            })
            .then(res => {
            this.tableData = res.data.data
            console.log(this.tableData)
            })
            .catch(error => {
            console.log(error)
            }) 
        },
        // gitlab 获取
        GitlabList(){
            axios.get(`${this.$config.url}/gitlab/repo_list`, {
                params: {
                'search': 'sgec'
                }
            })
            .then(res => {
                this.tableData = res.data.data
                console.log(this.tableData)
            })
            .catch(error => {
                console.log(error)
            }) 
        },
    },
    mounted(){
        this.rule_list()
    }
}
</script>