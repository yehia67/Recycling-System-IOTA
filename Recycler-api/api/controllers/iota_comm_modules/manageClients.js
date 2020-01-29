const mamManage = require('./testMam')
const addIPFS = require('./add')
const catIPFS = require('./cat')
const products = {}
const getLastHash = (AsciiArray) =>{
    const lastHash = AsciiArray[AsciiArray.length-1]
    return lastHash.substring(1,lastHash.length-1)
}
const initializePropreries = async()=>{
    products['HELLODRAYMANRNFELVTJREBCXJETCFEUGXBZHEGHCJHIYIFPQEQGDXILJZYUQMOYSELXIG9KUYOSYBFAY'] = {
       'name':'product01',
       'address':'HELLODRAYMANRNFELVTJREBCXJETCFEUGXBZHEGHCJHIYIFPQEQGDXILJZYUQMOYSELXIG9KUYOSYBFAY',
       'owner':false,
       'producer':'client01'
    }
    products['AB9CCUACOSC9VHQQFSVUGOVLAGNYJYDPROPBYTZIGNVOF9KMFNXBMSUGFFA9HTVHBPKPGGIBGSRJPUXZK'] = {
        'name':'product02',
        'address':'AB9CCUACOSC9VHQQFSVUGOVLAGNYJYDPROPBYTZIGNVOF9KMFNXBMSUGFFA9HTVHBPKPGGIBGSRJPUXZK',
        'owner':false,
        'producer':'client01'
    }
    products['LHSQELEGSRM9IAQCUBPXOLYXDMFOAPNJFS9JPOJBSRUDJAIRTWHTZCJCFLVYVRJPGPNXEWDDRGTCGKRAB'] = {
        'name':'product03',
        'address':'LHSQELEGSRM9IAQCUBPXOLYXDMFOAPNJFS9JPOJBSRUDJAIRTWHTZCJCFLVYVRJPGPNXEWDDRGTCGKRAB',
        'owner':false,
        'producer':'client01'
    }
    const ipfsHash = await addIPFS.execute(products)
    const root = await mamManage.send(ipfsHash)
    return root
}
const addNewClient = async (_root,_products)=>{
    const currentPropretiesRoot = _root
    const currentPropretiesHash = await mamManage.fetch(currentPropretiesRoot)
    const currentPropretiesString = await catIPFS.execute(getLastHash(currentPropretiesHash))
    const currentPropretiesJSON = JSON.parse(currentPropretiesString)
    const productsJSON = JSON.parse(JSON.stringify(_products))
    for(let i = 0; i < productsJSON.length;i++){
        currentPropretiesJSON[productsJSON[i].address] = productsJSON[i]
    }
    const newPropretiesHash = await addIPFS.execute(currentPropretiesJSON)
    const newPropretiesRoot = await mamManage.send(newPropretiesHash)
    return newPropretiesRoot
}
const addNewOwner = async(_root,productAddress,ownerAddress)=>{
    const proprietiesHash = await mamManage.fetch(_root)
    const currentPropretiesString = await catIPFS.execute(getLastHash(proprietiesHash))
    const currentPropretiesJSON = JSON.parse(currentPropretiesString)
    console.log('new Owner added succufully 200OK')
    console.log(currentPropretiesJSON)
    currentPropretiesJSON[productAddress].owner = ownerAddress 
    console.log(currentPropretiesJSON)
    const newPropretiesHash = await addIPFS.execute(currentPropretiesJSON)
    const root = await mamManage.send(newPropretiesHash)
    return root
}
const init = async()=>{
   productsArr=[
    {
    'address':'GBWOKDFLITNEXFBRHMVHJVRNCBWXAKCOVCKMOQZDQJCTGNG9NQQCDI9VJYWMZBXDQIKBVMKCWRAEKJGGR',
    'name':'nenwproduct01',
    'owner':false,
    'producer':'newClient01',
   },
   {
    'address':'HDWTUITQLQ9ANIU99KTHCHBNXXJECLNAEBOTWAGRRVVUPTUAAELITGHAKQTHI9QCQBWVXGROQSVKAQPXS',
    'name':'nenwproduct02',
    'owner':false,
    'producer':'newClient01',
   },
   {
    'address':'NNE9EOQNTNHWKGPYGLKPGUHLSJHQPQNYDZPXLQDREEXUCGIHEKGGZZCBQPKAZSVIJRRTNVOIXTHJQXGFY',
    'name':'nenwproduct02',
    'owner':false,
    'producer':'newClient01',
   }
   ]
   const initRoot = await initializePropreries()
   console.log('initialize root',initRoot)
   const newClientProprities = await addNewClient(initRoot,productsArr)
   console.log('new Client root',newClientProprities)
   const newOwnerPropritiesRoot = await addNewOwner(initRoot,'GBWOKDFLITNEXFBRHMVHJVRNCBWXAKCOVCKMOQZDQJCTGNG9NQQCDI9VJYWMZBXDQIKBVMKCWRAEKJGGR','new owner adddress')
   console.log('new owner root',newOwnerPropritiesRoot)
   const newOwnerPropritiesHash = await mamManage.fetch(newOwnerPropritiesRoot)
   const newOwnerPropritiesString = await catIPFS.execute(getLastHash(newOwnerPropritiesHash))
   const newOwnerProprities = JSON.parse(newOwnerPropritiesString)
   return newOwnerProprities


}
  /* init().then(function(r){
    console.log('**************************************')
    console.log(r)
})    */
module.exports ={
    init:initializePropreries,
    addNewClient:addNewClient,
    addNewOwner:addNewOwner
}

