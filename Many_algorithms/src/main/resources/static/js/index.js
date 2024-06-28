new Vue({
    el: '#app',
    data: {
        message: 'Hello Vue.js!',
        isFirstToPush: true,
    },
    methods: {

        handleUploadSuccess(response, file, fileList) {
            console.log("上传成功:", response);
            // this.$message.success('上传成功:' + response.data.message);
            this.$message({
                type:'success',
                message: '上传成功:' + response.fileName
            });
            this.isFirstToPush = false;
            console.log("还原是否第一次上传");
        },
        before_upload(file) {

        },

        // 上传文件失败时触发
        handleUploadError(err, file, fileList) {
            console.error("上传失败:", err);
            this.$message.error('上传失败:' + err);
        },
        // 文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用
        handleUploadChange(file, fileList) {

            console.log("文件状态改变:", file, fileList);
            console.log("文件名:" + file.name);
            console.log("文件大小:" + file.size);
            if(this.isFirstToPush){
                this.$confirm(`是否上传文件: ${file.name}?`, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$message({
                        type: 'success',
                        message: '开始上传!'
                    });
                    // 调用上传方法
                    this.$refs.upload.submit();

                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消上传'
                    });
                });
            }


        },

        //文件超出个数限制时的钩子
        on_exceed(files, fileList) {
            this.$message.error('文件超出个数限制:' + files.length);
        }

    },
    mounted() {
        var myModal = new bootstrap.Modal(document.getElementById('myModal'));
        myModal.show();
    }

})