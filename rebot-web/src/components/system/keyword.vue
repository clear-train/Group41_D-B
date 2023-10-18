<template>
  <div>
    <h1>Image Acquisition</h1>
    <div v-for="(image, index) in images" :key="index" class="image-container">
      <img :src="require(`@/images/${image}`)" alt="Image" class="image-size" />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      images: [], // 存放图片文件名的数组
    };
  },
  mounted() {
    // 初始加载图片
    this.loadImages();
  },
  methods: {
    loadImages() {
      // 假设你有一个包含图片文件名的数组
      this.images = ['1.jpg', '2.jpg', '3.jpg','4.jpg','5.jpg','6.jpg','7.jpg','8.jpg','9.jpg','10.jpg'];
    },
  },
};
</script>

<style scoped>
.image-container {
  margin: 10px;
  padding: 10px;
  border: 1px solid #ccc;
},
<style scoped>
.image-size {
  width: 10px; /* 设置宽度 */
  height: 20px; /* 设置高度 */
}
</style>