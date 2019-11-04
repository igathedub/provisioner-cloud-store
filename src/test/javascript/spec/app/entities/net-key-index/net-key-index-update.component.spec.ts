import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { NetKeyIndexUpdateComponent } from 'app/entities/net-key-index/net-key-index-update.component';
import { NetKeyIndexService } from 'app/entities/net-key-index/net-key-index.service';
import { NetKeyIndex } from 'app/shared/model/net-key-index.model';

describe('Component Tests', () => {
  describe('NetKeyIndex Management Update Component', () => {
    let comp: NetKeyIndexUpdateComponent;
    let fixture: ComponentFixture<NetKeyIndexUpdateComponent>;
    let service: NetKeyIndexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [NetKeyIndexUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NetKeyIndexUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NetKeyIndexUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NetKeyIndexService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NetKeyIndex(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new NetKeyIndex();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
