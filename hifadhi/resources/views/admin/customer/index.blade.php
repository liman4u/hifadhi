@extends('admin.layouts.default')

{{-- Web site Title --}}
@section('title') Customer :: @parent @stop

{{-- Content --}}
@section('main')
    <div class="page-header">
        <h3>
            Customer
            <div class="pull-right">
                <div class="pull-right">
                </div>
            </div>
        </h3>
    </div>

    <table id="table" class="table table-striped table-hover">
        <thead>
        <tr>
            <th>Name</th>
            <th>Phone Number</th>
            
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
