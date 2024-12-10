captcha<template>
    <h1 style='text-align:center;'>
      <Icon type="ios-home" size="80"></Icon>
      <span> Reset Password On OJS </span> 
    </h1>
    <br><br><br>
    <div class="demo-login">
          <Login @on-submit="handleSubmitReset" >
              <Email name="email"/>
              <Input prefix="md-at" placeholder="请再次输入以确认邮箱" style="width: 500px; height:50px" v-model="email_again" /><br>
              <Button type="primary" @click="handleGetEmailCaptcha" >Send Captcha</Button><br><br>
              <Input prefix="md-attach" placeholder="邮箱验证码" style="width: 500px; height:50px" v-model="email_captcha"/><br>
              <UserName name="username" />
              <Password name="password" placeholder="请输入新密码（至少六位）"/>
              <Password name="passwordAgain" placeholder="请再次输入以确认密码"/>
              <Captcha class="demo-login-captcha" name="captcha" :count-down="4" @on-get-captcha="handleGetCaptcha" placeholder="请输入图形验证码">
                  <template #text>
                    <img :src="'./captcha/' + this.captcha" />
                  </template>
              </Captcha>
              <Submit />
          </Login>
    </div>
    <br><br>
    <Footer> </Footer>
  </template>
  
  <script>
      import Footer from '@/components/Footer.vue'
      import axios from 'axios';
      export default {
          components: {
              Footer
          },
          data () {
              return {
                  captcha: 'captcha' + Math.floor(Math.random() * 1000) % 20 + '.jpg',
                  email: '',
                  email_again: '',
                  username: '',
                  password: '',
                  passwordAgain: '',
                  captcha_val: '',
                  email_captcha: '',
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
                          content: 'email: ' + mail + ' | username: ' + user_name + ' | password: ' + pass_word + ' | passwordAgain: ' + password_Again + ' | captcha: ' + captcha_value,
                            onOk: () => {
                                this.handleClickReset();
                            }
                      });
                  }
              },
              handleGetCaptcha () {
                this.captcha = 'captcha' + Math.floor(Math.random() * 1000) % 20 + '.jpg';
              },
              handleGetEmailCaptcha() {
                console.log(this.email_again);
                    axios.post('/api/email_captcha', {
                            email: this.email_again,
                    }).then((response) => {
                        console.log(response);
                        if (response.data.result === 'success') {
                            this.$Message.info('获取验证码成功');
                        } else {
                            this.$Message.error('获取验证码失败');
                        }
                    }).catch((error) => {
                        console.log(error);
                        this.$Message.error('后端接口调用失败');
                    });
              },
              handleClickReset() {
                if (this.email != this.email_again){
                    this.$Message.error('两次输入的邮箱不一致');
                      this.$router.push({ path: '/failure' });
                      return;
                }
                  if (this.password != this.passwordAgain) {
                      this.$Message.error('两次输入的密码不一致');
                      this.$router.push({ path: '/failure' });
                      return;
                  }
                  if (this.password.length < 6) {
                      this.$Message.error('密码长度不能小于6位');
                      this.$router.push({ path: '/failure' });
                      return;
                  }
                  if (this.captcha_val == this.captcha_val_dict[this.captcha]) {
                      axios.post('/api/reset_password', {
                            email: this.email,
                            username: this.username,
                            password: this.password,
                            email_captcha: this.email_captcha,
                      }).then((response) => {
                          console.log(response);
                          if (response.data.result === 'success') {
                              this.$Message.success('密码重置成功');
                              this.$router.push({ path: '/login' });
                          } else {
                              this.$Message.error('密码重置失败');
                              this.$router.push({ path: '/failure' });
                          }
                      }).catch((error) => {
                          console.log(error);
                          this.$Message.error('后端接口调用失败');
                          this.$router.push({ path: '/failure' });
                      });
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
  