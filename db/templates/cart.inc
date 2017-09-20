<?php

class price {
    var $id;
    var $price = 0.0;
}



// Line item present in the cart
class item {
    var $id;
    var $description;
    var $image;
    var $qty = 0;
    var $price = 0.0;
    var $weight = 0;
    var $url;
    var $verzendkosten;
    
    function extendedPrice() {
        return ($this->qty * $this->price);
    }
}


// Main class containing items
class cart {
    var $items = array();
    var $pricelist = array();
    var $verzendkosten = array();
    var $descriptionlist = array();
    var $imageURLlist = array();
    var $detailURLlist = array();
    var $kledingstukken = array();
    var $ontwerpen = array();
    var $shirtkleuren = array();

    var $username;

    function loadPrices() {
$this->pricelist["s.001"]=550;
$this->descriptionlist["s.001"]="Spreukenboekje Marijn van Hulten";
$this->verzendkosten["s.001"]=245;
$this->imageURLlist["s.001"]="../images/boek.png";
$this->detailURLlist["s.001"]="../spreukenboekje/spreukenboekje.html";

%PRICES%
%DESCRIPTIONS%
%VERZENDKOSTEN%
%IMAGE_URL%
%DETAIL_URL%
%KLEDINGSTUKKEN%
%ONTWERPEN%
%SHIRTKLEUREN%
    }

		
    function getNbLineItems() {
        return count($this->items);
    }

	function getTotalWeight() {
		$totalWeight=0;
        foreach ($this->items as $id => $item) {			
            $totalWeight = $totalWeight + ($item->qty * $item->weight);
        }
		return $totalWeight;		
	}

    function getTotalItems() {
        $totalItems=0;
        foreach ($this->items as $id => $item) {
            $totalItems = $totalItems + $item->qty;
        }
        return $totalItems;
    }

    function getDiscountPercentage(){
      $korting=0;
      $handle = @fopen("../admin/data/administratie/users/".$this->username."/kortingspercentage", "r");
      if ($handle) {
	while (!feof($handle))	{
		$korting = fgets($handle, 4096); // Read a line.
	}
	fclose($handle); // Close the file.
      }
      return $korting;
    }

    function hasDiscount(){
      return ($this->getDiscountPercentage()!=0);
    }
    

    function getTotalDiscount() {
        $discount=0;

        $totalPriceItems=0;

        foreach ($this->items as $id => $item) {
			$totalPriceItems = $totalPriceItems + $item->extendedPrice();
        }        
        
        $discount=0-$totalPriceItems*$this->getDiscountPercentage();
        $discount=$discount / 100;
        return $discount;
    }

    function StartsWith($Haystack, $Needle){
        // Recommended version, using strpos
        return strpos($Haystack, $Needle) === 0;
    }    

    function getShippingCosts() {
        $shippingPrice=0;
        $reproductionShippingCosts=0;
    
        foreach ($this->items as $id => $item) {
        	if ($this->StartsWith($item->id,"4.")) {
        	  if ($reproductionShippingCosts<$item->verzendkosten){
        	    $reproductionShippingCosts=$item->verzendkosten;
        	  }
        	}
        	else{
        	  if ($shippingPrice<$item->verzendkosten){
        	    $shippingPrice=$item->verzendkosten;
        	  }
        	}
        }

	// add the shirt/tas/trui/schilderij costs with the repro shipping consts (wordt apart verstuurd!)
        return $shippingPrice+$reproductionShippingCosts;
    }

    function getTotalPrice() {
        $totalPrice=$this->getShippingCosts()+$this->getTotalDiscount();

        foreach ($this->items as $id => $item) {
			$totalPrice = $totalPrice + $item->extendedPrice();
        }
        return $totalPrice;
    }
    
    
    function isEmpty() {
        return ($this->getNbLineItems()==0);
    }
    
    function getItemQty($id) {
        return $this->items[$id]->qty;
    }
    
    function setItemQty($id, $qty) {
		if ($qty == 0) {
			$this->removeItem($id);
		} else {
			$this->items[$id]->qty = $qty;
		}
    }

    function addItem($id, $qty) {
    	// if item is unknown, use the '0' ID
    	$origID = $id;
    	if ($this->descriptionlist[$id]==null){
    		$id="0";
    	}
    
    
        // If item exists, simply add more to it
        if ($this->getItemQty($id) > 0) {
            $this->setItemQty($id, $this->getItemQty($id) + $qty);
        }
        else {

	    $this->items[$id] = new item();
            $this->items[$id]->id = $id;
            $this->items[$id]->description = $this->descriptionlist[$id];
            $this->items[$id]->image = $this->imageURLlist[$id];
            $this->items[$id]->qty = $qty;
            $this->items[$id]->price = $this->pricelist[$id]/100;
            $this->items[$id]->url = $this->detailURLlist[$id];
            $this->items[$id]->verzendkosten = $this->verzendkosten[$id]/100;

            if ($id=="0"){
            	$this->items[$id]->description = "Unknown (".$origID.")";
            }

            
        }
    }
    
    function removeItem($id) {
        if (!$this->isEmpty())  {
            $tmp=array();
			foreach ($this->items as $idList => $item) {
                if ($idList != $id) {
                    $tmp[$idList] = $item;
                }
            };
            $this->items=$tmp;
        };
    }
	
	function removeAll() {
		$this->items=array();
	}
}
?>
