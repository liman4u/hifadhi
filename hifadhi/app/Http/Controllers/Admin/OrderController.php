<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\AdminController;
use App\Order;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Auth;
use Datatables;

class OrderController extends AdminController {

    public function __construct()
    {
        view()->share('type', 'order');
    }
     /*
    * Display a listing of the resource.
    *
    * @return Response
    */
    public function index()
    {
        // Show the page
        return view('admin.menuitemorder.index');
    }

    
    /**
     * Update the specified resource in storage.
     *
     * @param  int  $id
     * @return Response
     */
    public function update(MenuRequest $request, Menu $menu)
    {
        $menu -> user_id = Auth::id();
        $picture = "";
        if(Input::hasFile('image'))
        {
            $file = Input::file('image');
            $filename = $file->getClientOriginalName();
            $extension = $file -> getClientOriginalExtension();
            $picture = sha1($filename . time()) . '.' . $extension;
        }
        $menu -> picture = $picture;
        $menu -> update($request->except('image'));

        if(Input::hasFile('image'))
        {
            $destinationPath = public_path() . '/images/menuitem/'.$menuitem->id.'/';
            Input::file('image')->move($destinationPath, $picture);
        }
    }
   

    
    /**
     * Remove the specified resource from storage.
     *
     * @param $id
     * @return Response
     */

    public function delete(Order $order)
    {
        return view('admin.menuitemorder.delete', compact('order'));
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param $id
     * @return Response
     */
    public function destroy(Order $order)
    {
        $order->delete();
    }
    
    /**
     * Show a list of all the languages posts formatted for Datatables.
     *
     * @return Datatables JSON
     */
    public function data()
    {
        $orders = Order::join('menu_items', 'menu_items.id', '=', 'orders.menu_item_id')
                ->select(array('orders.id','orders.order_code','orders.status','menu_items.name as menuitem', 
                'orders.created_at'))->orderBy('orders.id', 'DESC')->get();
       

        return Datatables::of($orders)
           ->add_column('actions', '<a href="{{{ URL::to(\'admin/menuitemorder/\' . $id . \'/edit\' ) }}}" class="btn btn-success btn-sm iframe" ><span class="glyphicon glyphicon-pencil"></span>  Confirm</a>
               <a href="{{{ URL::to(\'admin/menuitemorder/\' . $id . \'/delete\' ) }}}" class="btn btn-sm btn-danger iframe"><span class="glyphicon glyphicon-trash"></span> {{ trans("admin/modal.delete") }}</a>
                <input type="hidden" name="row" value="{{$id}}" id="row">')
            ->remove_column('id')

            ->make();
    }

    


   

    
}
