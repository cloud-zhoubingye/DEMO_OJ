<style>
.layout{
    border: 1px solid #c693eb;
    background: hsl(275, 26%, 91%);
    position: relative;
    border-radius: 4px;
    overflow: hidden;
}
.layout-logo{
    width: 80px;
    height: 50px;
    background: #7523ab;
    border-radius: 2px;
    float: left;
    position: relative;
    top: 5px;
    left: 50px;
}
.layout-top-sider{
    width:550px;
    left: 80px;
    margin-left: 670px;
    color: #fff;
}
.demo-split{
        height: 200px;
        border: 1px solid #dcdee2;
}
.demo-split-pane{
    padding: 10px;
}
.demo-split-pane.no-padding{
    height: 200px;
    padding: 0;
}
</style>
<template>
    <div class="layout">
        <Layout>
            <Header>
                <Menu mode="horizontal" theme="dark" active-name="1">
                    <div class="layout-logo"> 
                        <img src="../../public/background/proj_icon.png" alt="logo" style="width: 100%; height: 100%;"/>
                    </div>
                    <div class="layout-top-sider">
                        <MenuItem name="1" @click="on_select_categorized_exercises">
                            <Icon type="ios-navigate"></Icon>
                            用户管理
                        </MenuItem>                        
                        <MenuItem name="2" @click="on_select_hard_based_exercises">
                            <Icon type="ios-navigate"></Icon>
                            题目修改与查看
                        </MenuItem>
                        <MenuItem name="3" @click="on_select_add">
                            <Icon type="ios-navigate"></Icon>
                            题目增加与删除
                        </MenuItem>
                    </div>
                </Menu>
            </Header>
            <Table stripe :columns="columns" :data="data">
                <template #name="{ row, index }">
                <Input type="text" v-model="editName" v-if="editIndex === index" />
                <span v-else>{{ row.name }}</span>
                </template>

                <template #mail="{ row, index }">
                <Input type="text" v-model="editEmail" v-if="editIndex === index" />
                <span v-else>{{ row.mail }}</span>
                </template>

                <template #password="{ row, index }">
                <Input type="text" v-model="editPassword" v-if="editIndex === index" />
                <span v-else>********</span>
                </template>

                <template #type="{ row, index }">
                <Input type="text" v-model="editType" v-if="editIndex === index" />
                <span v-else>{{ row.type }}</span>
                </template>

                <template #action="{ row, index }">
                <div v-if="editIndex === index">
                    <Button @click="handleSave(index)">Save</Button>
                    <Button @click="editIndex = -1">Cancel</Button>
                </div>
                <div v-else>
                    <Button @click="handleEdit(row, index)">Change</Button>
                </div>
                </template>
            </Table>
        </Layout>
    </div>
    <Footer> </Footer>
</template>

<script>
    import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
    import Footer from '@/components/Footer.vue'
import axios from 'axios';
    export default {
        components: { Editor, Toolbar, Footer },
        data () {
            return {
                columns: [
                    {
                        title: 'UserName',
                        slot: 'name'
                    },
                    {
                        title: 'Email',
                        slot: 'mail'
                    },
                    {
                        title: 'Password',
                        slot: 'password'
                    },
                    {
                        title: 'AccountType',
                        slot: 'type'
                    },
                    {
                        title: 'Operation',
                        slot: 'action'
                    }
                    ],
                    data: [
                    {
                        name: 'Bingye Zhou',
                        mail: 'xxc8865@gamil.com',
                        password: '123456',
                        type: 'User'
                    },
                    {
                        name: 'Yunfan Zhang',
                        mail: 'zhou_bingye@outlook.com',
                        password: '123456789',
                        type: 'User'
                    },
                    {
                        name: 'Liaotian Feng',
                        mail: 'zhou_bingye@163.com',
                        password: '654321',
                        type: 'User'
                    },
                    {
                        name: 'Admin',
                        mail: 'zhou_bingye@163.com',
                        password: '654321',
                        type: 'Administrator'
                    }
                ],
                editIndex: -1,  // 当前聚焦的输入框的行数
                editName: '',  // 第一列输入框，当然聚焦的输入框的输入内容，与 data 分离避免重构的闪烁
                editEmail: '',  // 第二列输入框
                editType: '',  // 第四列输入框
                editPassword: '',  // 第三列输入框
            }
        },
        mounted() {
            axios.get('/api/userManagement')
                .then(response => {
                    this.data = response.data;
                })
                .catch(error => {
                    console.log(error);
                    this.$Message.error('Failed to get user list data');
                });
        },
        methods: {
            on_select_categorized_exercises() {
                this.$router.push({ path: '/admin_user' });
            },
            on_select_hard_based_exercises() {
                this.$router.push({ path: '/admin_problem' });
            },
            on_select_add() {
                this.$router.push({ path: '/admin_add_problem' });
            },
            handleEdit (row, index) {
                this.editName = row.name;
                this.editEmail = row.mail;
                this.editPassword = row.password;
                this.editType = row.type;
                this.editIndex = index;
            },
            handleSave (index) {
                if (this.editType != 'User' && this.editType != 'Administrator') {
                    this.$Message.error('Type must be User or Administrator');
                    return;
                }
                this.data[index].name = this.editName;
                this.data[index].mail = this.editEmail;
                this.data[index].type = this.editType;
                this.data[index].password = this.editPassword;
                this.editIndex = -1;
                axios.post('/api/userManagement', this.data[index])
                    .then(response => {
                        this.$Message.success('Save successfully');
                    })
                    .catch(error => {
                        console.log(error);
                        this.$Message.error('Failed to save user data');
                    });
            },
        },
    }

</script>
