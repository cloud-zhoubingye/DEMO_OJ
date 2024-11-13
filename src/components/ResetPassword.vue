<template>
    <h1 style='text-align:center;'>
      <Icon type="ios-home" size="80"></Icon>
      <span> Reset Password On OJS </span> 
    </h1>
    <br><br><br><br>
    <div class="demo-login">
          <Login @on-submit="handleSubmitReset" >
              <Email name="email"/>
              <UserName name="username" />
              <Password name="password" />
              <Password name="passwordAgain" />
              <Captcha class="demo-login-captcha" name="captcha" :count-down="4" @on-get-captcha="handleGetCaptcha">
                  <template #text>
                    <img :src="'./captcha/' + this.captcha" />
                  </template>
              </Captcha>
              <Submit @click="handleClickReset" />
          </Login>
          <br><br>
    </div>
    <br><br><br><br>
    <Footer> </Footer>
  </template>
  
  <script>
      import Footer from '@/components/Footer.vue'
  
      export default {
          components: {
              Footer
          },
          data () {
              return {
                  captcha: 'captcha' + Math.floor(Math.random() * 1000) % 20 + '.jpg',
                  email: '',
                  username: '',
                  password: '',
                  passwordAgain: '',
                  captcha_val: '',
                  captcha_val_dict: {
                      'captcha0.jpg': 'a2af',
                      'captcha1.jpg': 'n5wn',
                      'captcha2.jpg': 'nn27',
                      'captcha3.jpg': 'mn6b',
                      'captcha4.jpg': 'mwe2',
                      'captcha5.jpg': 'pamb',
                      'captcha6.jpg': 'm37w',
                      'captcha7.jpg': 'e56b',
                      'captcha8.jpg': '6yg7',
                      'captcha9.jpg': '78g5',
                      'captcha10.jpg': 'g654',
                      'captcha11.jpg': 'gx5b',
                      'captcha12.jpg': 'ddbm',
                      'captcha13.jpg': '4y24',
                      'captcha14.jpg': 'gfdf',
                      'captcha15.jpg': 'yxmm',
                      'captcha16.jpg': 'e66y',
                      'captcha17.jpg': 'n38g',
                      'captcha18.jpg': 'nbpa',
                      'captcha19.jpg': 'ey77',
                  }
              }
          },
          methods: {
              handleSubmitReset (valid, { email: mail, username: user_name, password: pass_word, passwordAgain: password_Again, captcha: captcha_value }) {
                  if (valid) {
                      this.email = mail;
                      this.username = user_name;
                      this.password = pass_word;
                      this.passwordAgain = password_Again;
                      this.captcha_val = captcha_value;
                      this.$Modal.info({
                          title: '请确认您的更改密码信息：',
                          content: 'email: ' + mail + ' | username: ' + user_name + ' | password: ' + pass_word + ' | passwordAgain: ' + password_Again + ' | captcha: ' + captcha_value
                      });
                  }
              },
              handleGetCaptcha () {
                this.captcha = 'captcha' + Math.floor(Math.random() * 1000) % 20 + '.jpg';
              },
              handleClickReset() {
                  console.log(this.username);
                  console.log(this.password);
                  
                  if (this.password != this.passwordAgain) {
                      this.$Message.error('两次输入的密码不一致');
                      this.$router.push({ path: '/failure' });
                  }

                  if (this.captcha_val == this.captcha_val_dict[this.captcha]) {
                      if (this.username == 'Bingye Zhou' && this.email == 'xxc8865@gmail.com') {
                          // 保存用户和密码到localStorage
                          localStorage.setItem('username', this.username);
                          localStorage.setItem('password', this.password);
                          this.$router.push({ path: '/success' });
                      } 
                      else {
                          this.$Message.error('用户名或密码错误');
                          this.$router.push({ path: '/failure' });
                      }
                  } 
                  else {
                      this.$Message.error('验证码错误');
                      this.$router.push({ path: '/failure' });
                  }
              },
          }
      }
  </script>
  
  <style>
      .demo-login{
          width: 500px !important;
          margin: 0 auto;
      }
      .demo-login-captcha .ivu-btn{
          padding: 0;
      }
      .demo-login-captcha .ivu-btn img{
          height: 28px;
          position: relative;
          top: 4px;
      }
  </style>
  