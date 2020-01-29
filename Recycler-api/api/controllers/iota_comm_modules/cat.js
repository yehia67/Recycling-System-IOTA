const ipfsLibrary = require('ipfs-http-client')
const ipfs = new ipfsLibrary({ host: 'ipfs.infura.io', port: 5001, protocol: 'https' })
const cat = async (hash) =>{
   const content = await ipfs.cat(hash)
    console.log("read hash =",hash)
    return content.toString()
}
/*cat('QmYhGkWQ1V1bThZjvvX8qzBH66rAkCteVKRy7kcKtBFhoD').then(function(r){
    console.log(r)
})*/
module.exports = {
    execute : cat
}