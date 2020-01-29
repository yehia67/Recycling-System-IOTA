////////////////////////////////////////////////
// Send a microtransaction
////////////////////////////////////////////////
const iotaGlobal = require('./IotaGlobal')
// Create a wrapping function so you can use async/await
const sendToken = async (_receivingAddress,_value) => {



// Define an input transaction object
// that sends 1 i to your new address
  const transfers = [
    {
      value: _value,
      address: _receivingAddress
    }
  ];

  console.log('Sending 1 i to ' + _receivingAddress);

  try {
    // Construct bundle and convert to trytes
    const trytes = await iotaGlobal.iota.prepareTransfers(iotaGlobal.seed, transfers);
    // Send bundle to node.
    const response = await iotaGlobal.iota.sendTrytes(trytes, iotaGlobal.depth, iotaGlobal.minimumWeightMagnitude);

    console.log('Bundle sent');
    response.map(tx => console.log(tx));
  } catch (error) {
    console.log(error);
  }
}
module.exports ={
    execute:sendToken
}
/*  sendToken('ALNUSDMZBXOKCZSVKULZRRFZYXKHZJMBXQATHJRAUEXSBIVLUMZMYWNCEHFRLZGFFPUAGYBNAOVOQHWBA',1).then(function(){
   console.log('done')
 });  */