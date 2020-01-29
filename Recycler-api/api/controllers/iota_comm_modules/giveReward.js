const sendToken = require('./sendToken')
const fetchToMap = require('./fetchToMap')
giveRewards = async(root,productAddress) =>{
      const OwnerAddrress = await fetchToMap.execute(root)
      console.log('----------')
      console.log(OwnerAddrress[productAddress].owner)
      await  sendToken.execute(OwnerAddrress[productAddress].owner,1)
      return "Send 1 i. Congrats"  
}
/*   giveRewards('FLPYQZOAFZ9COLVBO9LJNZJYIWJJKDDQGZMHYJSLLNTANN9QWFUCQRLUVDQVBNTZUPKNAJAJKAODQVIYN','HDWTUITQLQ9ANIU99KTHCHBNXXJECLNAEBOTWAGRRVVUPTUAAELITGHAKQTHI9QCQBWVXGROQSVKAQPXS').then(function(r){
console.log(r)
})    */
module.exports ={
    execute:giveRewards
}
