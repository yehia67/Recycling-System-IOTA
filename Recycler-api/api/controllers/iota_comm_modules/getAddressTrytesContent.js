///////////////////////////////
// MAM: Publish messages to Private Stream
///////////////////////////////
const IotaGlobal = require('./IotaGlobal')


// Init State
const mamType = 'restricted'
const mamSecret = 'DONTSHARETHISPASSWORD'

// Initialise MAM State
let mamState = IotaGlobal.Mam.init('https://nodes.devnet.iota.org:443')
/**
 * 
 * @param {the root address} _root 
 * @param {option = 0 user need the trytescontent option = 1 user need messge of trytescontent} option 
 */
const FetchMam = async (_root,option) => {
  // Output syncronously once fetch is completed
  if(option === 0){
    const result = await IotaGlobal.Mam.fetchSingle(_root, mamType, mamSecret)
    return  result.payload
  }
  else{
    const result = await IotaGlobal.Mam.fetch(_root, mamType, mamSecret)
    return result.messages
  }
 
}
 /*    FetchMam("GSRMRCCHBRFXJOFBQDXPKLWDRZTHHBVVYPHZZAUXDKIPVRG9DDRIQBTQCTTCJZPHPQZLCIMPNEYBJUIYZ",0).then(function(r){
  console.log(r)
 }) */   
module.exports ={
  execute:FetchMam
}
