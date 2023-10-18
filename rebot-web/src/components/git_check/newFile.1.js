import axios from "axios";

export default (await import("vue")).defineComponent({
  data() {
    return {
      //筛选条件
      equipmentNum: "",
      useBy: "",
      belongTo: "",
      equipmentStatus: "",
      networkStatus: "",
      tableData: [],

      tableColumns: [
        {
          title: "Id",
          key: "equipmentId"
        },
        {
          title: "资产编号",
          key: "equipmentNum"
        },
        {
          title: "序列号",
          key: "serialNum"
        },
        {
          title: "设备状态",
          key: "equipmentStatus"
        },
        {
          title: "网络状态",
          key: "networkStatus"
        },
        {
          title: "资产描述",
          key: "refer"
        },
        {
          title: "所属人",
          key: "belongTo"
        },
        {
          title: "使用人",
          key: "useBy"
        },
        {
          title: "开始使用时间",
          key: "startUse"
        },
        {
          title: "供应商",
          key: "brand"
        },
        {
          title: "规格型号",
          key: "specification"
        },
        {
          title: "细项",
          key: "component"
        },
        {
          title: "成本中心",
          key: "costCenter"
        },
        {
          title: "SN号",
          key: "sn"
        },
        {
          title: "成本中心地址",
          key: "centerAddress"
        },
        {
          title: "IP",
          key: "ip"
        },
        {
          title: "操作系统",
          key: "operatingSystem"
        },
        {
          title: "创建时间",
          key: "createTime"
        },
        {
          title: "修改时间",
          key: "updateTime"
        }
      ],
      account: 0,
      pageSize: 10,
      pageIndex: 1,
      ruledata: [],
      git_type: [
        {
          value: "github",
          label: "github"
        },
        {
          value: "gitlab",
          label: "gitlab"
        },
        {
          value: "gitee",
          label: "gitee"
        }
      ],
      value: "",
      Height: "",
      visible: false
    };
  },

  mounted() {
    this.pageChange();
  },

  methods: {
    filterTable() {
      this.pageIndex = 1; // 重置分页索引为第一页
      this.pageChange();
    },

    pageChange(pageIndex) {
      if (pageIndex) this.pageIndex = pageIndex;
      const params = {
        pageSize: this.pageSize,
        pageIndex: this.pageIndex,
        equipmentNum: this.equipmentNum || null,
        useBy: this.useBy || null,
        belongTo: this.belongTo || null,
        equipmentStatus: this.equipmentStatus || null,
        networkStatus: this.networkStatus || null
      };

      axios
        .get(`${this.$config.url}/equipment/search`, {
          params
        })
        .then(res => {
          this.tableData = res.data.equipmentList;
          this.account = res.data.account;
        })
        .catch(error => {
          console.log(error);
        });
    },

    Getdata() {
      if (this.value != "") {
        if (this.value == "github") {
          this.GithubList();
        } else if (this.value == "gitlab") {
          this.GitlabList();
        } else if (this.value == "gitee") {
          this.GiteeList();
        }
      }
    },
    // github 获取
    GithubList() {
      axios
        .get(`${this.$config.url}/github/repo_list`, {
          params: {
            q: "95598pay"
          }
        })
        .then(res => {
          this.tableData = res.data.data.github;
        })
        .catch(error => {
          console.log(error);
        });
    },
    //触发开启定时任务
    Cronstart() {
      axios
        .post(`${this.$config.url}/git/trigger_start`)
        .then(res => {
          if (res.data.code == 0) {
            this.$message.success(res.data.msg);
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    // 规则列表
    rule_list() {
      axios
        .get(`${this.$config.url}/git/rule_list`)
        .then(res => {
          console.log(res.data);
          this.ruledata = res.data.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    // gitee 获取
    GiteeList() {
      axios
        .get(`${this.$config.url}/gitee/repo_list`, {
          params: {
            q: "sgec"
          }
        })
        .then(res => {
          this.tableData = res.data.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    // gitlab 获取
    GitlabList() {
      axios
        .get(`${this.$config.url}/gitlab/repo_list`, {
          params: {
            search: "sgec"
          }
        })
        .then(res => {
          this.tableData = res.data.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    //git扫描信息获取
    gitinfo() {
      axios
        .get(`${this.$config.url}/git/gitinfo`)
        .then(res => {
          this.tableData = res.data.data;
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
});
