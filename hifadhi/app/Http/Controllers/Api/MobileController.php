<?php namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Log;
use App\Restaurant;


class MobileController extends Controller {

    public function getRestaurants() {

        Log::info("Getting restaurants ");
        
        try{
        $restaurants =  Restaurant::all();
        Log::info("Restaurants -> " .$restaurants);

         return '{"marker": ' . json_encode($restaurants) . '}';  
         } catch(Exception $e) {
		return '{"error":{"text":'. $e->getMessage() .'}}'; 
	}
 
        
    }
    
 

   public function searchItems(Request $request) {

        $search_item = $request->input('search_item');
        

        Log::info("Searching items :: search item ->".$search_item);


        if($search_item == null){
             return '{"error":{"text":"Enter a search item"}}'; 
        }else{
            
            $restaurants =  Restaurant::join('menu_categories', 'menu_categories.restaurant_id', '=', 'restaurants.id')
                            ->select(DB::raw("*,"))
                            ->whereRaw('menu_categories.name like "%'.$search_item.'%" ')
                            ->orderBy('restaurants.id', 'DESC')->get();

            

            
            return '{"marker": ' . json_encode($restaurants) . '}'; 
            
        }


        
    }



}
