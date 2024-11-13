<template>
    <h1 style='text-align:center;'>
      <Icon type="ios-home" size="80"></Icon>
      <span> Online Judge System Register </span> 
    </h1>
    <br><br><br><br>
    <div class="demo-reg">
          <Login @on-submit="handleSubmit" >
              <Email name="mail"/>
              <UserName name="username"/>
              <Password name="password"/>
              <Password name="passwordConfirm" placeholder="确认密码" />
              <Captcha class="demo-login-captcha" name="captcha" :count-down="4" @on-get-captcha="handleGetCaptcha">
                  <template #text>
                    <img :src="'./captcha/' + this.captcha" />
                  </template>
              </Captcha>
              <Submit @click="handleClick" />
          </Login>
          <br><br>
    </div>
    <br><br><br><br>
    <Footer> </Footer>
  </template>
  
  <script>
      import Footer from '@/components/Footer.vue'
  import { Button, Email, Icon, Time } from 'view-ui-plus';
      export default {
          components: {
              Footer
          },
          data () {
              return {
                  captcha: 'captcha' + Math.floor(Math.random() * 1000) % 20 + '.jpg',
                  username: '',
                  password: '',
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
              handleSubmit (valid, { mail: email, username: user_name, password: pass_word, passwordConfirm: pass_word_conf, captcha: captcha_value }) {
                  if (valid) {
                      this.$Modal.info({
                          title: '请确认您的注册信息：',
                          content: 'email: ' + email + ' | username: ' + user_name + ' | password: ' + pass_word + ' | captcha: ' + captcha_value
                      });
                      this.email = email;
                      this.username = user_name;
                      this.password = pass_word;
                      this.passwordConfirm = pass_word_conf;
                      this.captcha_val = captcha_value;
                  }
              },
              handleGetCaptcha () {
                this.captcha = 'captcha' + Math.floor(Math.random() * 1000) % 20 + '.jpg';
              },
              handleClick() {
                  console.log(this.username);
                  console.log(this.password);
                  console.log(this.email);
                  console.log(this.captcha_val);
                  
                    if (this.captcha_val == this.captcha_val_dict[this.captcha]) {
                        if (this.password != this.passwordConfirm) {
                            // 休息一秒钟
                            this.$Message.error('两次输入的密码不一致');
                            this.$router.push({ path: '/failure' });
                        }
                        else {
                            // 保存用户和密码到localStorage
                            localStorage.setItem('email', this.email);
                            localStorage.setItem('username', this.username);
                            localStorage.setItem('password', this.password);
                            this.$router.push({ path: '/success' });
                        }
                    } 
                    else {
                        this.$router.push({ path: '/failure' });
                    }
              },
          }
      }
  </script>
  
  <style>
      .demo-reg{
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
  