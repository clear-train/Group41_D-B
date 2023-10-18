<template>
  <div>
    <div>
      <label>Record Id:</label>
      <input v-model="equipmentId" type="text" placeholder="Record Id" />
    </div>
    <div>
      <label>Equipment Number:</label>
      <input v-model="equipmentNum" type="text" placeholder="Equipment Number" />
    </div>
    <div>
      <label>User ID:</label>
      <input v-model="serialNum" type="text" placeholder="User ID" />
    </div>
    <div>
      <label>Describe:</label>
      <input v-model="refer" type="text" placeholder="Describe" />
    </div>
    <div>
      <label>User:</label>
      <input v-model="belongTo" type="text" placeholder="User" />
    </div>
    <div>
      <label>Equipment Name:</label>
      <input v-model="useBy" type="text" placeholder="Equipment Name" />
    </div>
    <div>
      <label>Start Use:</label>
      <input v-model="startUse" type="text" placeholder="Start Use" />
    </div>
    <div>
      <label>Exploration duration:</label>
      <input v-model="brand" type="text" placeholder="Exploration duration" />
    </div>
    <div>
      <label>Treasures:</label>
      <input v-model="specification" type="text" placeholder="Treasures" />
    </div>
    <div>
      <label>Photo:</label>
      <input v-model="component" type="text" placeholder="Photo" />
    </div>
    <div>
      <label>Location:</label>
      <input v-model="costCenter" type="text" placeholder="Location" />
    </div>
    <div>
      <label>View Camera:</label>
      <input v-model="sn" type="text" placeholder="View Camera" />
    </div>
    <div>
      <label>Map:</label>
      <input v-model="centerAddress" type="text" placeholder="Map" />
    </div>
    <div>
      <label>IP:</label>
      <input v-model="ip" type="text" placeholder="IP" />
    </div>
    <div>
      <label>Others:</label>
      <input v-model="operatingSystem" type="text" placeholder="Others" />
    </div>
    <div>
      <label>Equipment Status</label>
      <input v-model="equipmentStatus" type="radio" name="equipmentStatus" value=1> Run
      <input v-model="equipmentStatus" type="radio" name="equipmentStatus" value=2> Stop
      <label>探索宝藏：</label>
      <input v-model="networkStatus" type="radio" name="networkStatus" value=1> Book
      <input v-model="networkStatus" type="radio" name="networkStatus" value=2> Key
      <input v-model="networkStatus" type="radio" name="networkStatus" value=3> Rubik's cube
    </div>
    <button @click="submitData">Submit</button>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data () {
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
    }
  },
  mounted () {
    this.submitData()
  },
  methods: {
    submitData () {
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
      }

      // 发送POST请求到后端接口
      axios
        .post(`${this.$config.url}/equipment/insert`, params)
        .then((res) => {
          console.log(res)
        })
        .catch((error) => {
          console.log(error)
        })
    },
  }
}
</script>
