const initClient = require('../initClients')
//Test Cases
const testCases = async() =>{
 //await initClient.execute("CLIENTID00001","IJX9QD9ZHZZA9KPULHAWEEXWWKZAFJVMARKORRNTYYJIVUMESPOJBOPSSIQEHJICGPDQJPSG9DGRZTRWV")
 const getProducts = await getOwners()
 console.log(getProducts)
}
     
testCases()