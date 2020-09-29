const ws = require('nodejs-websocket')
const PORT = 8090

const server = ws.createServer(connect => {
    console.log("有新连接");
    // connect.on('text', data =>{
    //     connect.send(data.toUpperCase());
    // })
    //
    // connect.on('close', ()=>{
    //     console.log("有人断开连接");
    // })
    //
    // connect.on('error', ()=>{
    //     console.log("异常");
    // })
})

function broadcast(msg) {
    server.connections.forEach(item => {
        item.send(msg)
    })
}

server.listen(PORT, '10.10.10.28', () => {
    console.log("已触发监听事件")
})