import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProvisionercloudTestModule } from '../../../test.module';
import { AllocatedSceneRangeDeleteDialogComponent } from 'app/entities/allocated-scene-range/allocated-scene-range-delete-dialog.component';
import { AllocatedSceneRangeService } from 'app/entities/allocated-scene-range/allocated-scene-range.service';

describe('Component Tests', () => {
  describe('AllocatedSceneRange Management Delete Component', () => {
    let comp: AllocatedSceneRangeDeleteDialogComponent;
    let fixture: ComponentFixture<AllocatedSceneRangeDeleteDialogComponent>;
    let service: AllocatedSceneRangeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [AllocatedSceneRangeDeleteDialogComponent]
      })
        .overrideTemplate(AllocatedSceneRangeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllocatedSceneRangeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllocatedSceneRangeService);
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
