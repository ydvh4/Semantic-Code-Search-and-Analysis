<?php 
$find=$_GET["find"];
$keyword=$_GET["keyword"];
if($find=="JPackage")
{
$data = json_decode(file_get_contents("http://localhost:8080/CodeSearch/rest/json/searchpack?keyword=$keyword"));
}
if($find=="JMethod")
{
$hv1=$_GET["hv1"];
$thr=$_GET["thr"];
$rt=$_GET["rt"];
$data = json_decode(file_get_contents("http://localhost:8080/CodeSearch/rest/json/searchmethod?keyword=$keyword&hv1=$hv1&rt=$rt&thr=$thr"));
}
if($find=="JClass")
{
$ec=$_GET["ec"];
$ic=$_GET["ic"];
$cm=$_GET["cm"];
$hv=$_GET["hv"];
$data = json_decode(file_get_contents("http://localhost:8080/CodeSearch/rest/json/searchclass?keyword=$keyword&ec=$ec&ic=$ic&cm=$cm&hv=$hv"));
}
if($find=="JConstructor")
{
$data = json_decode(file_get_contents("http://localhost:8080/CodeSearch/rest/json/searchcon?keyword=$keyword"));
}
if($find=="JInterface")
{
$data = json_decode(file_get_contents("http://localhost:8080/CodeSearch/rest/json/searchint?keyword=$keyword"));
}
$template= file_get_contents('template.html');
		
		$stream = '';
		foreach($data as $t) {
				
			$html = $template;
			
			$html = str_replace('[text]',
			$t,$html);
			
			$stream .= $html;
		}
			
		print $stream;
		

?>