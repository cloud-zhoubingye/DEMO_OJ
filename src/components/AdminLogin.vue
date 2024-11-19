<template>
    <h1 style='text-align:center;'>
      <Icon type="ios-home" size="80"></Icon>
      <span> Online Judge System Admin Login </span> 
    </h1>
    <br><br><br><br>
    <div class="demo-login">
          <Login @on-submit="handleSubmit" >
              <UserName name="username" value="Bingye Zhou" />
              <Password name="password" value="123456" />
              <Captcha class="demo-login-captcha" name="captcha" :count-down="4" @on-get-captcha="handleGetCaptcha">
                  <template #text>
                    <img :src="'./captcha/' + this.captcha" />
                  </template>
              </Captcha>
              <Submit />
          </Login>
          <br><br>
          忘记了密码？立即找回！-> -> ->
        <Button type="primary" @click="handleClickPasswd" style="margin-left: 10px;">Reset Password</Button>
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
              handleSubmit (valid, { username: user_name, password: pass_word, captcha: captcha_value }) {
                if (valid) {
                    this.username = user_name;
                    this.password = pass_word;
                    this.captcha_val = captcha_value;
                    this.$Modal.info({
                        title: '请确认您的登录信息：',
                        content: 'username: ' + user_name + ' | password: ' + pass_word + ' | captcha: ' + captcha_value, 
                        onOk: () => { this.handleClick(); }
                    });
                  }
              },
              handleGetCaptcha () {
                this.captcha = 'captcha' + Math.floor(Math.random() * 1000) % 20 + '.jpg';
              },
              handleClick() {
                  console.log(this.username);
                  console.log(this.password);
                  
                  if (this.captcha_val == this.captcha_val_dict[this.captcha]) {
                      if (this.username == 'Bingye Zhou' && this.password == '123456') {
                          // 保存用户和密码到localStorage
                          localStorage.setItem('username', this.username);
                          localStorage.setItem('password', this.password);
                          localStorage.setItem('isAdminLogin', true);
                          this.$Message.success('Login Success!');  
                          this.$router.push({ path: '/admin_user' });
                      } 
                      else {
                          this.$router.push({ path: '/failure' });
                      }
                  } 
                  else {
                      this.$router.push({ path: '/failure' });
                  }
              },
              handleClickPasswd() {
                localStorage.setItem('resetPasswordAdmin', true);
                this.$router.push({ path: '/reset_password' });
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
  