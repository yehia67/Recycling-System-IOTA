const iotaGlobal = require("./IotaGlobal")

const generateAddresses = async (index,securityLevel,numberOfAddresses) =>{
   return Promise.resolve(iotaGlobal.iota.getNewAddress(iotaGlobal.seed, { index: index, securityLevel: securityLevel, total: numberOfAddresses })
    .then(address => {
        return address
    })
    .catch(err => {
      console.log(err)
    }));
}
//TTWAFVTWJKUQKNA9KPZFZCIECTRGUFDDJSTSYQSQOCGQJCSDIAVZANJTZBLOHAD9VPAQJMZTIFIDCHJXD
 generateAddresses(0,3,1).then(function(r){
    console.log(r)
    })
      
module.exports ={
    execute:generateAddresses
}
 
