@extends('admin.layouts.default')

{{-- Web site Title --}}
@section('title') Order :: @parent @stop

{{-- Content --}}
@section('main')
    <div class="page-header">
        <h3>
            Order
            <div class="pull-right">
                <div class="pull-right">
                </div>
            </div>
        </h3>
    </div>

    <table id="table" class="table table-striped table-hover">
        <thead>
        <tr>
            <th>Order Code</th>
            <th>Menu Item</th>
            
            <th>{{ trans("admin/admin.created_at") }}</th>
            <th>{{ trans("admin/admin.action") }}</th>
        </tr>
        </thead>
        <tbody></tbody>
    </table>
@stop

{{-- Scripts --}}
@section('scripts')
@stop
