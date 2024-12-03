// Not Used
<template>
  <div style="border: 1px solid #ccc">
    <Toolbar
      style="border-bottom: 1px solid #ccc"
      :editor="editorRef"
      :defaultConfig="toolbarConfig"
      :mode="mode"
    />
    <Editor
      style="height: 500px; overflow-y: hidden;"
      v-model="valueHtml"
      :defaultConfig="editorConfig"
      :mode="mode"
      @onCreated="handleCreated"
    />
  </div>
</template>

<script>
import '@wangeditor/editor/dist/css/style.css' 

import { onBeforeUnmount, ref, shallowRef, onMounted } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'

export default {
  name: 'TextEditor',
  components: { Editor, Toolbar },
  setup() {

    const editorRef = shallowRef()


    const valueHtml = ref('<p>hello</p>') // 初始化内容


    onMounted(() => {
        // setTimeout(() => {
        //     valueHtml.value = '<p>请输入程序代码...</p>'
        // }, 1500)
    })

    const toolbarConfig = {}
    const editorConfig = { placeholder: '请输入程序代码...' }


    onBeforeUnmount(() => { // 组件销毁时销毁编辑器
        const editor = editorRef.value
        if (editor == null) return
        editor.destroy()
    })

    const handleCreated = (editor) => {
      editorRef.value = editor 
    }

    return {
      editorRef,
      valueHtml,
      mode: 'default', 
      toolbarConfig,
      editorConfig,
      handleCreated
    };
  }
}
</script>    