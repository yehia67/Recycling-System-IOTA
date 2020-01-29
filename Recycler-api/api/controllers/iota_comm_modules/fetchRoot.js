const Mam = require('@iota/mam')
// Init State
const mamType = 'restricted'
const mamSecret = 'DONTSHARETHISPASSWORD'
const iotaGlobal = require('./IotaGlobal')
const { asciiToTrytes, trytesToAscii } = require('@iota/converter')
// Initialise MAM State
let mamState =Mam.init('https://nodes.devnet.iota.org:443')

// Callback used to pass data out of the fetch
const logData = data => console.log( trytesToAscii(data))

const fetchRoot = async(_root)=>{
    const resp = await  Mam.fetch(_root, mamType, mamSecret, logData)
    const tryteMessages = resp.messages
    const asciiMessages = []
    for (let index = 0; index < tryteMessages.length; index++) {
        asciiMessages.push(trytesToAscii(tryteMessages[index]))
    }
     return asciiMessages
}
const test = async()=>{
    
    const result = await fetchRoot('TRETKSTEFKWKGHNVVPUIS99IIRJKAGDTUZQKCHAWEIIOZMEHPUXZXDXSQDSZVY9WAWXXUTM9ACYEIJXCF')
    console.log(result)
}
//test()
module.exports = {
    execute:fetchRoot
}