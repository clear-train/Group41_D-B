import axios from 'axios';

export default (await import('vue')).defineComponent({
data() {
return {
equipmentId: '',
equipmentNum: '',
serialNum: '',
refer: '',
belongTo: '',
useBy: '',
startUse: '',
brand: '',
specification: '',
component: '',
costCenter: '',
sn: '',
centerAddress: '',
ip: '',
operatingSystem: '',
equipmentStatus: '',
networkStatus: ''
};
},
mounted() {
this.submitData();
},
methods: {
submitData() {
// 构建要发送的数据对象
const params = {
equipmentId: this.equipmentId,
equipmentNum: this.equipmentNum,
serialNum: this.serialNum,
refer: this.refer,
belongTo: this.belongTo,
useBy: this.useBy,
startUse: this.startUse,
brand: this.brand,
specification: this.specification,
component: this.component,
costCenter: this.costCenter,
sn: this.sn,
centerAddress: this.centerAddress,
ip: this.ip,
operatingSystem: this.operatingSystem,
equipmentStatus: Number(this.equipmentStatus),
networkStatus: Number(this.networkStatus)
};

// 发送POST请求到后端接口
axios
.post(`${this.$config.url}/equipment/insert`, {
params
})
.then((res) => {
console.log(res);
})
.catch((error) => {
console.log(error);
});
}
}
});
