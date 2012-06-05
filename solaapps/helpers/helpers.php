<?php
function get_domain()  
{  
	$domain = "14.198.178.221";  
	return $domain;  
}
function get_link_class($link)
{
	if(strrpos("/".$_SERVER['REQUEST_URI']."/", $link))
		return "active";
	else
		return "normal";
}