import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { ProvisionercloudTestModule } from '../../../test.module';
import { AllocatedSceneRangeComponent } from 'app/entities/allocated-scene-range/allocated-scene-range.component';
import { AllocatedSceneRangeService } from 'app/entities/allocated-scene-range/allocated-scene-range.service';
import { AllocatedSceneRange } from 'app/shared/model/allocated-scene-range.model';

describe('Component Tests', () => {
  describe('AllocatedSceneRange Management Component', () => {
    let comp: AllocatedSceneRangeComponent;
    let fixture: ComponentFixture<AllocatedSceneRangeComponent>;
    let service: AllocatedSceneRangeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [AllocatedSceneRangeComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(AllocatedSceneRangeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AllocatedSceneRangeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllocatedSceneRangeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AllocatedSceneRange(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.allocatedSceneRanges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AllocatedSceneRange(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.allocatedSceneRanges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should re-initialize the page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AllocatedSceneRange(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);
      comp.reset();

      // THEN
      expect(comp.page).toEqual(0);
      expect(service.query).toHaveBeenCalledTimes(2);
      expect(comp.allocatedSceneRanges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
  });
});
