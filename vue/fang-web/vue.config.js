module.exports = {
    publicPath: './',
    outputDir: 'yanghongyuUser',
    devServer: {
        port: '9000',
        proxy: {
            "/api": {
                target: 'http://localhost:8080',
                pathRewrite: {
                  '^/api': '/'
                }
            }
        }
    }
}