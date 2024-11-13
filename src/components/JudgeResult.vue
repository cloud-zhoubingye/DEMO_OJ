<template>
    <br>
    <br>
    <Skeleton loading avatar animated  :paragraph="para" style="margin-left: 8%;" />
    
</template>
<script>
    export default {
        data () {
            return {
                para: { rows: 3, width: [200, 1000, '300px', '50%'] },
            }
        }
    }
</script>
