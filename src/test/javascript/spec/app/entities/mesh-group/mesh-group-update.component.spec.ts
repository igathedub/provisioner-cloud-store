import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { MeshGroupUpdateComponent } from 'app/entities/mesh-group/mesh-group-update.component';
import { MeshGroupService } from 'app/entities/mesh-group/mesh-group.service';
import { MeshGroup } from 'app/shared/model/mesh-group.model';

describe('Component Tests', () => {
  describe('MeshGroup Management Update Component', () => {
    let comp: MeshGroupUpdateComponent;
    let fixture: ComponentFixture<MeshGroupUpdateComponent>;
    let service: MeshGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [MeshGroupUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MeshGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MeshGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MeshGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MeshGroup(123);
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
        const entity = new MeshGroup();
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
