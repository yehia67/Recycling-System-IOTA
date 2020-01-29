
const initClients = require('./manageClients')
const getAddressTrytesContent = require('./getAddressTrytesContent')
setProductOwner = async (ownerAddress,productAddress) =>{
    try{
    const trytes = await getAddressTrytesContent.execute(productAddress,0)
     const result = await initClients.setOwner(ownerAddress,trytes)
     return result
    }catch(exception){
          console.log(exception)
    }
}
module.exports ={
    execute:setProductOwner
}