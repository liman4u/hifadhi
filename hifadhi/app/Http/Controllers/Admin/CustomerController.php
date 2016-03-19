<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\AdminController;
use App\Customer;
use Illuminate\Support\Facades\Input;
use Illuminate\Support\Facades\Auth;
use Datatables;

class CustomerController extends AdminController {

    public function __construct()
    {
        view()->share('type', 'customer');
    }
     /*
    * Display a listing of the resource.
    *
    * @return Response
    */
    public function index()
    {
        // Show the page
        return view('admin.customer.index');
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return Response
     */
    public function create() {
        return view('admin.customer.create_edit');
    }

    
    /**
     * Remove the specified resource from storage.
     *
     * @param $id
     * @return Response
     */

    public function delete(Customer $customer)
    {
        return view('admin.customer.delete', compact('customer'));
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param $id
     * @return Response
     */
    public function destroy(Customer $customer)
    {
        $customer->delete();
    }
    
    /**
     * Show a list of all the languages posts formatted for Datatables.
     *
     * @return Datatables JSON
     */
    public function data()
    {
        $customers = Customer::select(array('customers.id','customers.name','customers.phone_number', 
                'customers.created_at'))->orderBy('customers.id', 'DESC')->get();
       

        return Datatables::of($customers)
           ->add_column('actions', '<a href="{{{ URL::to(\'admin/customer/\' . $id . \'/delete\' ) }}}" class="btn btn-sm btn-danger iframe"><span class="glyphicon glyphicon-trash"></span> {{ trans("admin/modal.delete") }}</a>
                <input type="hidden" name="row" value="{{$id}}" id="row">')
            ->remove_column('id')

            ->make();
    }

    /**
     * Reorder items
     *
     * @param items list
     * @return items from @param
     */
    public function getReorder(ReorderRequest $request) {
        $list = $request->list;
        $items = explode(",", $list);
        $order = 1;
        foreach ($items as $value) {
            if ($value != '') {
                MenuItem::where('id', '=', $value) -> update(array('position' => $order));
                $order++;
            }
        }
        return $list;
    }


   

    
}
