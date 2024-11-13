<template>
  <br>
  <h1 style='text-align:center;'>
    <Icon type="ios-paper" size="80"></Icon>
    <span>Online Judge System</span>
  </h1>
  <br><br>
  <Carousel autoplay v-model="value" loop style="text-align:center;">
      <CarouselItem>
          <div class="demo-carousel">
            <img src="../../public/background/acm1.jpg" alt="通知公告1" style="width: 500px; height: 250px;">
          </div>
      </CarouselItem>
      <CarouselItem>
          <div class="demo-carousel">
            <img src="../../public/background/acm2.jpg" alt="通知公告2" style="width: 500px; height: 250px;">
          </div>
      </CarouselItem>
      <CarouselItem>
          <div class="demo-carousel">
            <img src="../../public/background/acm3.jpg" alt="通知公告3" style="width: 500px; height: 250px;">
          </div>
      </CarouselItem>
      <CarouselItem>
          <div class="demo-carousel">
            <img src="../../public/scuacm.png" alt="通知公告4" style="width: 300px; height: 250px;">
          </div>
      </CarouselItem>

  </Carousel>

  <div class="start">
    <Button type="primary" @click="handleClickUser" style="margin-left: 0%;">User  Login</Button>
    <Button type="primary" @click="handleClickAdmin" style="margin-left: 10%;">Admin Login</Button>
  </div>

  <Footer> </Footer>
</template>

<script>
import Footer from '@/components/Footer.vue'
export default {
  name: 'OnlineJudgeSystem',
  components: {
    Footer
  },
  setup() {
  },
  methods: {
    handleClickUser() {
      this.$Message.info('Welcome to OJS at ' + new Date().toLocaleString() + '!');
      this.$router.push({ path: '/login' });
    },
    handleClickAdmin() {
      this.$Message.info('Welcome to OJS at ' + new Date().toLocaleString() + '!');
      this.$router.push({ path: '/admin_login' });
    }
  }
}
</script>

<style lang="less" scoped>
.start{
  font-size: @font-size;
  text-align: center;
  margin: 60px 0 0 0;
}
</style>


