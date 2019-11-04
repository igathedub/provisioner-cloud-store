import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProvisionercloudTestModule } from '../../../test.module';
import { MeshGroupDeleteDialogComponent } from 'app/entities/mesh-group/mesh-group-delete-dialog.component';
import { MeshGroupService } from 'app/entities/mesh-group/mesh-group.service';

describe('Component Tests', () => {
  describe('MeshGroup Management Delete Component', () => {
    let comp: MeshGroupDeleteDialogComponent;
    let fixture: ComponentFixture<MeshGroupDeleteDialogComponent>;
    let service: MeshGroupService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [MeshGroupDeleteDialogComponent]
      })
        .overrideTemplate(MeshGroupDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MeshGroupDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MeshGroupService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
