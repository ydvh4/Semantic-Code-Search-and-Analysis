// jQuery script for search request with server
$(document).ready(function() {
$( "#search" ).click(function() {

		
		// Display a progress indicator
		$('#search_results').html('<img src="ajax_loader.gif"> Searching...');
		var find = $( "#find" ).val();
		var ic = $('#ic').val();
		var cm = $('#cm').val();
		var hv = $('#hv').val();
		var ec = $('#ec').val();
		var thr = $('#thr').val();
		var hv1 = $('#hv1').val();
		var rt = $('#rt').val();
		var cm1 = $('#cm1').val();
		var ca1 = $('#ca1').val();
		var cp1 = $('#cp1').val();
		var hv2 = $('#hv2').val();
		
		
		var keyword=$("#to").val();
		$.ajax({
			url: 'http://yashwanth.cloudapp.net/codesearch/tweet/search_server.php?find=' + find +'&keyword='+keyword+'&ec='+ec+'&ic='+ic+'&cm='+cm+'&hv='+hv+'&thr='+thr+'&hv1='+hv1+'&rt='+rt+'&cm1='+cm1+'&cp1='+cp1+'&hv2='+hv2+'&ca1='+ca1,
			success: function(data){
console.log(data);				
				// Display the results
				$('#search_results').html(data);
			}
		})
	
});
});